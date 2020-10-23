package cn.aaron911.log.server.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.slf4j.LoggerFactory;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.server.properties.DefaultInitProperty;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;


public class ElasticLowerClientSingleton {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ElasticLowerClientSingleton.class);
    private static ElasticLowerClientSingleton instance;
    private RestClient client;

    public static ElasticLowerClientSingleton getInstance(String hosts, String userName, String passWord) {
        if (instance == null) {
            synchronized (ElasticLowerClientSingleton.class) {
                if (instance == null) {
                    instance = new ElasticLowerClientSingleton(hosts, userName, passWord);
                }
            }
        }
        return instance;
    }

    /**
     * 带密码认证的
     *
     * @param hosts
     * @param userName
     * @param passWord
     */
    public ElasticLowerClientSingleton(String hosts, String userName, String passWord) {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord));  //es账号密码
        String[] hostsAndPorts = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostsAndPorts.length];
        for (int i = 0; i < hostsAndPorts.length; i++) {
            httpHosts[i] = HttpHost.create(hostsAndPorts[i]);
        }
        client = RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }).build();
    }

    /**
     * 带ssl认证的
     *
     * @param hosts
     * @param keyStorePass
     * @param sslFile
     * @param keyStoreName
     */
    public ElasticLowerClientSingleton(String hosts, String keyStorePass, String sslFile, String keyStoreName) {

        try {
            Path keyStorePath = Paths.get(sslFile);
            KeyStore truststore = KeyStore.getInstance(keyStoreName);
            try (InputStream is = Files.newInputStream(keyStorePath)) {
                truststore.load(is, keyStorePass.toCharArray());
            }
            SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(truststore, null);
            final SSLContext sslContext = sslBuilder.build();
            String[] hostsAndPorts = hosts.split(",");
            HttpHost[] httpHosts = new HttpHost[hostsAndPorts.length];
            for (int i = 0; i < hostsAndPorts.length; i++) {
                httpHosts[i] = HttpHost.create(hostsAndPorts[i]);
            }
            client = RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setSSLContext(sslContext);
                }
            }).build();
        } catch (Exception e) {
            logger.error("ElasticSearch init fail!", e);
        }
    }

    public boolean existIndice(String indice) {
        try {
            Request request = new Request("HEAD", "/" + indice + "");
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    public boolean creatIndice(String indice) {
        try {
            Request request = new Request(
                    "PUT",
                    "/" + indice + "");
            String properties = "\"properties\":{\"appName\":{\"type\":\"keyword\"}," +
                    "\"logLevel\":{\"type\":\"keyword\"}," +
                    "\"serverName\":{\"type\":\"keyword\"}," +
                    "\"traceId\":{\"type\":\"keyword\"}," +
                    "\"dataTimeStamp\":{\"type\":\"date\",\"format\":\"strict_date_optional_time||epoch_millis\"}" +
                    "}";
            String ent = "{\"settings\":{\"number_of_shards\":"+ DefaultInitProperty.ES_INDEX_SHARDS+",\"number_of_replicas\":"+DefaultInitProperty.ES_INDEX_REPLICAS+",\"refresh_interval\":\""+DefaultInitProperty.ES_REFRESH_INTERVAL+"\"}";
            ent = ent + ",\"mappings\":{"+properties+"}}";
            
            request.setJsonEntity(ent);
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                logger.info("creat indice {}",indice);
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }
    public boolean creatIndiceTrace(String indice) {
        try {
            Request request = new Request(
                    "PUT",
                    "/" + indice + "");
            String properties = "\"properties\":{\"appName\":{\"type\":\"keyword\"}," +
                    "\"traceId\":{\"type\":\"keyword\"}" +
                    "}";
            String ent = "{\"settings\":{\"number_of_shards\":"+ DefaultInitProperty.ES_INDEX_SHARDS+",\"number_of_replicas\":"+DefaultInitProperty.ES_INDEX_REPLICAS+",\"refresh_interval\":\""+DefaultInitProperty.ES_REFRESH_INTERVAL+"\"}";
            ent = ent + ",\"mappings\":{"+properties+"}}";
            
            request.setJsonEntity(ent);
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                logger.info("creat indice {}",indice);
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }
    
    public boolean creatIndiceNomal(String indice) {
        try {
            Request request = new Request("PUT", "/" + indice + "");
            String ent = "{\"settings\":{\"number_of_shards\":5,\"number_of_replicas\":0,\"refresh_interval\":\"10s\"}}";
            request.setJsonEntity(ent);
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }


    public void insertListLog(List<String> list, String baseIndex) {
        if (!existIndice(baseIndex)) {
            if(baseIndex.startsWith(LogMessageConstant.ES_INDEX)) {
                creatIndice(baseIndex);
            }else {
                creatIndiceNomal(baseIndex);
            }
            if (logger.isDebugEnabled()) {
            	logger.debug("creatIndex:{}", baseIndex);
            }
        }
        insertList(list,baseIndex);
    }
    
    
    public void insertListTrace(List<String> list, String baseIndex) {
        insertList(list,baseIndex);
    }
    public void insertListComm(List<String> list, String baseIndex) {
        insertList(list,baseIndex);
    }
    
    private void insertList(List<String> list, String baseIndex) {
        StringBuffer sendStr = new StringBuffer();
        for (int a = 0; a < list.size(); a++) {
            String map = list.get(a);
            String ent = "{\"index\":{} ";
            sendStr.append(ent);
            sendStr.append("\r\n");
            sendStr.append(map);
            sendStr.append("\r\n");
        }
        String endpoint = "/" + baseIndex + "/_bulk";

        Request request = new Request("PUT", endpoint);
        request.setJsonEntity(sendStr.toString());
        client.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    String responseStr = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    logger.error("ElasticSearch commit!", e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("ElasticSearch commit Failure!", e);
            }
        });
    }

    public String cat(String index) {
        String reStr = "";
        Request request = new Request( "GET", "/_cat/indices/" + index + "?v");
        try {
            Response res = client.performRequest(request);
            InputStream inputStream = res.getEntity().getContent();
            byte[] bytes = new byte[0];
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String str = new String(bytes);
            reStr = str;
        } catch (Exception e) {
            e.printStackTrace();
            reStr = "";
        }
        return reStr;
    }

    public String get(String url, String queryStr) {
        String reStr = "";
        StringEntity stringEntity = new StringEntity(queryStr, "utf-8");
        stringEntity.setContentType("application/json");
        Request request = new Request("GET", url);
        request.setEntity(stringEntity);
        try {
            Response res = client.performRequest(request);
            return EntityUtils.toString(res.getEntity(), "utf-8");
        } catch (Exception e) {
            reStr = "";
            e.printStackTrace();
        }
        return reStr;
    }

    public List<String> getExistIndices(String[] indices) {
        List<String> existIndexList = new ArrayList<String>();
        for (String index : indices) {
            try {
                Request request = new Request("HEAD", "/" + index + "");
                Response res = client.performRequest(request);
                if (res.getStatusLine().getStatusCode() == 200) {
                    existIndexList.add(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existIndexList;
    }

    public boolean deleteIndex(String index) {
        try {
            Request request = new Request("DELETE", "/" + index + "");
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            logger.error("", e);
        }
    }

}
