package yich.download.local;

import yich.base.logging.JUL;
import yich.base.resource.PropertyFiles;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Config extends PropertyFiles {
    final public static Logger logger = JUL.getLogger(Config.class);
    final public static Item CONFIG = item("_config/config.properties");
    public static Path DIR_CACHED;
    public static Path DIR_COLLECTED;
    public static Path DIR_GENERATED;
    public static int COLLECTOR_INTERVAL;
    public static boolean COLLECTOR_DEL_SRC;
    public static String FILE_DETECTOR;
    public static String MERGER_SUFFIX;
    public static String MERGER_TAG;
    public static boolean MERGER_DEL_SRC;

    static {
        try {
            DIR_CACHED = Paths.get(CONFIG.getProperty("dir.cached"));
            DIR_COLLECTED = Paths.get(CONFIG.getProperty("dir.collected"));
            DIR_GENERATED = Paths.get(CONFIG.getProperty("dir.generated"));
            COLLECTOR_INTERVAL = Integer.parseInt(CONFIG.getProperty("collector.interval"));
            COLLECTOR_DEL_SRC = Boolean.parseBoolean(CONFIG.getProperty("collector.delSrc"));
            FILE_DETECTOR = CONFIG.getProperty("collector.detector");
            MERGER_SUFFIX = CONFIG.getProperty("merger.suffix");
            MERGER_TAG = "".equals(MERGER_TAG = CONFIG.getProperty("merger.tag")) ? null : MERGER_TAG;
            MERGER_DEL_SRC = Boolean.parseBoolean(CONFIG.getProperty("merger.delSrc"));;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw e;
        }

    }

    public static void main(String[] args) {
        System.out.println("DIR_CACHED: " + DIR_CACHED);
        System.out.println("DIR_COLLECTED: " + DIR_COLLECTED);
        System.out.println("DIR_GENERATED: " + DIR_GENERATED);
        System.out.println("COLLECTOR_INTERVAL: " + COLLECTOR_INTERVAL);
        System.out.println("COLLECTOR_DEL_SRC: " + COLLECTOR_DEL_SRC);
        System.out.println("FILE_DETECTOR: " + FILE_DETECTOR);
        System.out.println("MERGER_SUFFIX: " + MERGER_SUFFIX);
        System.out.println("MERGER_TAG: " + MERGER_TAG);
        System.out.println("MERGER_DEL_SRC: " + MERGER_DEL_SRC);
    }
}
