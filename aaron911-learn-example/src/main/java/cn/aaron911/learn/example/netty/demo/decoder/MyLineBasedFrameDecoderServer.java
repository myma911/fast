/**
 * Welcome to https://waylau.com
 */
package cn.aaron911.learn.example.netty.demo.decoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * My LineBasedFrameDecoder Server。
 * 
 * @since 1.0.0 2019年12月12日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyLineBasedFrameDecoderServer {

	public static int DEFAULT_PORT = 8023;

	public static void main(String[] args) throws Exception {
		int port = DEFAULT_PORT;

		// 多线程事件循环器
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); // boss
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // worker

		try {
			// 启动NIO服务的引导程序类
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workerGroup) // 设置EventLoopGroup
					.channel(NioServerSocketChannel.class) // 指明新的Channel的类型
					.childHandler(new MyLineBasedFrameDecoderChannelInitializer()) // 指定ChannelHandler
					.option(ChannelOption.SO_BACKLOG, 128) // 设置的ServerChannel的一些选项
					.childOption(ChannelOption.SO_KEEPALIVE, true); // 设置的ServerChannel的子Channel的选项

			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();

			System.out.println("MyLineBasedFrameDecoderServer已启动，端口：" + port);

			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();
		} finally {

			// 优雅的关闭
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

}
