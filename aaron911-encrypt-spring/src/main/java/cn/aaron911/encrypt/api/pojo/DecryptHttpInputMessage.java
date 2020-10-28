package cn.aaron911.encrypt.api.pojo;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>解密信息输入流</p>
 * 
 */
public class DecryptHttpInputMessage implements HttpInputMessage {

    private InputStream body;

    private HttpHeaders headers;

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

	public DecryptHttpInputMessage(InputStream body, HttpHeaders headers) {
		super();
		this.body = body;
		this.headers = headers;
	}
    
    
}
