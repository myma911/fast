package cn.aaron911.esclientrhl.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import cn.aaron911.esclientrhl.util.Constant;

/**
 * 
 * description: 自动配置注入restHighLevelClient
 * 
 **/
@Configuration
@ComponentScan("cn.aaron911.esclientrhl")
public class ElasticSearchConfiguration  {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    private RestHighLevelClient restHighLevelClient;

    /**
     * 这个close是调用RestHighLevelClient中的close
     * 
     */
    @Bean(destroyMethod="close")
    @Scope("singleton")
    public RestHighLevelClient createInstance() {
        String host = elasticsearchProperties.getHost();
        String username = elasticsearchProperties.getUsername();
        String password = elasticsearchProperties.getPassword();
        try {
            if(StringUtils.isEmpty(host)){
                host = Constant.DEFAULT_ES_HOST;
            }
            String[] hosts = host.split(",");
            HttpHost[] httpHosts = new HttpHost[hosts.length];
            for (int i = 0; i < httpHosts.length; i++) {
                String h = hosts[i];
                httpHosts[i] = new HttpHost(h.split(":")[0], Integer.parseInt(h.split(":")[1]), "http");
            }

            if(!StringUtils.isEmpty(username)) {
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(username, password));  //es账号密码（默认用户名为elastic）
                restHighLevelClient = new RestHighLevelClient(
                        RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })
                );
            }else{
                restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHosts));
            }
        } catch (Exception e) {
            logger.error("es 连接失败", e);
            return null;
        }
        return restHighLevelClient;
    }
}
