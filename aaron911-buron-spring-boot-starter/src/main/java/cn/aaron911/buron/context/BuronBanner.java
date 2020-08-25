package cn.aaron911.buron.context;

/**
 * @version 1.0
 */
public class BuronBanner {

    public static final String INIT_VERSION = "1.0.0";

    public static final String LOGO = "Buron~!";
            

    public static String buildBannerText() {
        return System.getProperty("line.separator") + LOGO + " :: Buron ::        (v" + BuronVersion.getVersion(INIT_VERSION) + ")" + System.getProperty("line.separator");
    }
}
