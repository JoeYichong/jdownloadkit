package yich.download.local;

public class FileCollectors {
    public static FileCollector newFileCollector() {
        return new FileCollector(Config.DIR_COPY_FROM, Config.DIR_COPY_TO)
                     .setFileDetector(FileDetectors.get(Config.COLLECTOR_DETECTOR))
                     .setInterval(Config.COLLECTOR_INTERVAL)
                     .setDelSrc(Config.COLLECTOR_DEL_SRC);
    }
}
