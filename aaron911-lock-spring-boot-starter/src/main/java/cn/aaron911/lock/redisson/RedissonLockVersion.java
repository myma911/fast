package cn.aaron911.lock.redisson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.CodeSource;

/**
 * 版本管理器
 */
public class RedissonLockVersion {

    private static final Logger log = LoggerFactory.getLogger(RedissonLockVersion.class);

    /**
     * 获取当前依赖版本
     *
     * @param defaultVersion 默认的版本
     * @return version
     */
    public static String getVersion(String defaultVersion) {
        try {
            Class clazz = RedissonLockVersion.class;
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
