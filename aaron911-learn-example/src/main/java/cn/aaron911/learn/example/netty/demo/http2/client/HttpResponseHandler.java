package cn.aaron911.learn.example.netty.demo.http2.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Process {@link io.netty.handler.codec.http.FullHttpResponse} translated from HTTP/2 frames
 *
 * @since 1.0.0 2019年12月29日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class HttpResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private final Map<Integer, Entry<ChannelFuture, ChannelPromise>> streamidPromiseMap;

    public HttpResponseHandler() {
        streamidPromiseMap = PlatformDependent.newConcurrentHashMap();
    }

    /**
     * Create an association between an anticipated response stream id and a {@link io.netty.channel.ChannelPromise}
     *
     * @param streamId The stream for which a response is expected
     * @param writeFuture A future that represent the request write operation
     * @param promise The promise object that will be used to wait/notify events
     * @return The previous object associated with {@code streamId}
     * @see HttpResponseHandler#awaitResponses(long, TimeUnit)
     */
    public Entry<ChannelFuture, ChannelPromise> put(int streamId, ChannelFuture writeFuture, ChannelPromise promise) {
        return streamidPromiseMap.put(streamId, new SimpleEntry<ChannelFuture, ChannelPromise>(writeFuture, promise));
    }

    /**
     * Wait (sequentially) for a time duration for each anticipated response
     *
     * @param timeout Value of time to wait for each response
     * @param unit Units associated with {@code timeout}
     * @see HttpResponseHandler#put(int, io.netty.channel.ChannelFuture, io.netty.channel.ChannelPromise)
     */
    public void awaitResponses(long timeout, TimeUnit unit) {
        Iterator<Entry<Integer, Entry<ChannelFuture, ChannelPromise>>> itr = streamidPromiseMap.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<Integer, Entry<ChannelFuture, ChannelPromise>> entry = itr.next();
            ChannelFuture writeFuture = entry.getValue().getKey();
            if (!writeFuture.awaitUninterruptibly(timeout, unit)) {
                throw new IllegalStateException("Timed out waiting to write for stream id " + entry.getKey());
            }
            if (!writeFuture.isSuccess()) {
                throw new RuntimeException(writeFuture.cause());
            }
            ChannelPromise promise = entry.getValue().getValue();
            if (!promise.awaitUninterruptibly(timeout, unit)) {
                throw new IllegalStateException("Timed out waiting for response on stream id " + entry.getKey());
            }
            if (!promise.isSuccess()) {
                throw new RuntimeException(promise.cause());
            }
            System.out.println("---Stream id: " + entry.getKey() + " received---");
            itr.remove();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        Integer streamId = msg.headers().getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text());
        if (streamId == null) {
            System.err.println("HttpResponseHandler unexpected message received: " + msg);
            return;
        }

        Entry<ChannelFuture, ChannelPromise> entry = streamidPromiseMap.get(streamId);
        if (entry == null) {
            System.err.println("Message received for unknown stream id " + streamId);
        } else {
        	// 处理消息（打印到控制台）
            ByteBuf content = msg.content();
            if (content.isReadable()) {
                int contentLength = content.readableBytes();
                byte[] arr = new byte[contentLength];
                content.readBytes(arr);
                System.out.println(new String(arr, 0, contentLength, CharsetUtil.UTF_8));
            }

            entry.getValue().setSuccess();
        }
    }
}
