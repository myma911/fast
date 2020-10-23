package cn.aaron911.log.server.monitor;

import cn.hutool.http.HttpUtil;
import java.util.*;

public class WechatClient {

    public static void sendToWeChat(LogMonitorTextMessage logMonitorTextMessage, String URL) {
        Map<String, Object> requestBody = new HashMap<>(2);
        Map<String, Object> content = new HashMap<>(2);
        content.put("content", logMonitorTextMessage.getText());
        requestBody.put("msgtype", "markdown");
        requestBody.put("markdown", content);
        HttpUtil.post(URL, requestBody);

        boolean atMobiles = logMonitorTextMessage.getAtMobiles() != null && logMonitorTextMessage.getAtMobiles().size()> 0;
        if (logMonitorTextMessage.isAtAll() || atMobiles) {
            requestBody.clear();
            content.clear();
            List<String> mobiles = logMonitorTextMessage.getAtMobiles();
            List<String> mobileList = new ArrayList<>();
            if (mobiles.size() > 0) {
                mobileList.addAll(mobiles);
            }
            if (logMonitorTextMessage.isAtAll()) {
                mobileList.add("@all");
            }
            content.put("mentioned_mobile_list", mobileList);
            content.put("content", "");
            requestBody.put("msgtype", "text");
            requestBody.put("text", content);
            HttpUtil.post(URL, requestBody);
        }

    }
}
