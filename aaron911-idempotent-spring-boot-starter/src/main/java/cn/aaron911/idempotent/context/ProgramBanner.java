package cn.aaron911.idempotent.context;

import java.security.CodeSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * springboot 启动输出 banner
 */
public class ProgramBanner {
	
    private static final Logger log = LoggerFactory.getLogger(ProgramBanner.class);

    public static final String INIT_VERSION = "1.0.0";

    public static final String LOGO = "Idempotent~!";
            

    public static String buildBannerText() {
        return System.getProperty("line.separator") + LOGO + " ::  (v" + getVersion(INIT_VERSION) + ")" + System.getProperty("line.separator");
    }
    
    
    
    /**
    *
    * @param defaultVersion 默认的版本
    * @return version
    */
   public static String getVersion(String defaultVersion) {
       try {
           Class<ProgramBanner> clazz = ProgramBanner.class;
           String version = clazz.getPackage().getImplementationVersion();
           if (version == null || version.length() == 0) {
               version = clazz.getPackage().getSpecificationVersion();
           }

           if (version == null || version.length() == 0) {
               CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
               if (codeSource == null) {
                   log.warn("No codeSource for class " + clazz.getName() + " when getVersion, use default version " + defaultVersion);
               } else {
                   String file = codeSource.getLocation().getFile();
                   if (file != null && file.length() > 0 && file.endsWith(".jar")) {
                       file = file.substring(0, file.length() - 4);
                       int i = file.lastIndexOf(47);
                       if (i >= 0) {
                           file = file.substring(i + 1);
                       }

                       i = file.indexOf("-");
                       if (i >= 0) {
                           file = file.substring(i + 1);
                       }

                       while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
                           i = file.indexOf("-");
                           if (i < 0) {
                               break;
                           }
                           file = file.substring(i + 1);
                       }
                       version = file;
                   }
               }
           }

           return version != null && version.length() != 0 ? version : defaultVersion;
       } catch (Throwable var6) {
           log.error("return default version, ignore exception " + var6.getMessage(), var6);
           return defaultVersion;
       }
   }

}
