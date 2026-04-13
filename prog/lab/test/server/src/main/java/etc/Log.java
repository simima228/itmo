package etc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger logger = LogManager.getLogger("server");
    private Log() {}
    public static void info(String msg) {
        logger.info(msg);
    }
    public static void warn(String msg) {
        logger.warn(msg);
    }
    public static void error(String msg) {
        logger.error(msg);
    }

}
