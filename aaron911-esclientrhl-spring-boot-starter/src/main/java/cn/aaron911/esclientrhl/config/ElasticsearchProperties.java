package cn.aaron911.esclientrhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: esclientrhl
 * @description:
 **/
@Component
@ConfigurationProperties("aaron911.elasticsearch")
public class ElasticsearchProperties {
    private String host = "127.0.0.1";
    private int port = 9200;
	private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
    public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
