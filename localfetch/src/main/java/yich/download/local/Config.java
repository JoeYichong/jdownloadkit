package yich.download.local;

import yich.base.logging.JUL;
import yich.base.resource.PropertyFiles;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Config extends PropertyFiles {
    final public static Logger logger = JUL.getLogger(Config.class);
    final public static Item CONFIG = item("_config/config.properties");
    public static Path DIR_COPY_FROM;
    public static Path DIR_COPY_TO;
    public static Path DIR_GEN_TO;
    public static int COLLECTOR_INTERVAL;
    public static boolean COLLECTOR_DEL_SRC;
    public static String FILE_DETECTOR;
    public static String MERGER_SUFFIX;
    public static String MERGER_TAG;
    public static boolean MERGER_DEL_SRC;

    static {
        try {
            DIR_COPY_FROM = Paths.get(CONFIG.getProperty("dir.copy.source"));
            DIR_COPY_TO = Paths.get(CONFIG.getProperty("dir.copy.destination"));
            DIR_GEN_TO = Paths.get(CONFIG.getProperty("dir.gen.destination"));
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
        System.out.println("DIR_COPY_FROM: " + DIR_COPY_FROM);
        System.out.println("DIR_COPY_TO: " + DIR_COPY_TO);
        System.out.println("DIR_GEN_TO: " + DIR_GEN_TO);
        System.out.println("COLLECTOR_INTERVAL: " + COLLECTOR_INTERVAL);
        System.out.println("COLLECTOR_DEL_SRC: " + COLLECTOR_DEL_SRC);
        System.out.println("FILE_DETECTOR: " + FILE_DETECTOR);
        System.out.println("MERGER_SUFFIX: " + MERGER_SUFFIX);
        System.out.println("MERGER_TAG: " + MERGER_TAG);
        System.out.println("MERGER_DEL_SRC: " + MERGER_DEL_SRC);
    }
}
