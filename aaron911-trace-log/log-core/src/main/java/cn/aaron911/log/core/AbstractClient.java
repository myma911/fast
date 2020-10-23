package cn.aaron911.log.core;


import java.util.List;

import cn.aaron911.log.core.exception.LogQueueConnectException;


public abstract class AbstractClient {

    private static AbstractClient client;

    public void pushMessage(String key, String strings) throws LogQueueConnectException {
    }
    public void putMessageList(String key, List<String> list) throws LogQueueConnectException{}
    public static AbstractClient getClient() {
        return client;
    }

    public static void setClient(AbstractClient abstractClient) {
        client = abstractClient;
    }
}
