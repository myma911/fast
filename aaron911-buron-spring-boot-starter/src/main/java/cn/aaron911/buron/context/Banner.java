package cn.aaron911.buron.context;


public class Banner {

    public static final String INIT_VERSION = "1.0.0";

    public static final String LOGO = "Buron~!";
            

    public static String buildBannerText() {
        return System.getProperty("line.separator") + LOGO + INIT_VERSION + System.getProperty("line.separator");
    }
}
