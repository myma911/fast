package cn.aaron911.idempotent.context;

public class ProgramBanner {

    public static final String INIT_VERSION = "1.0.0";
    public static final String LOGO = "Idempotent~!";

    public static String buildBannerText() {
        return System.getProperty("line.separator") + LOGO + INIT_VERSION + System.getProperty("line.separator");
    }
    

}
