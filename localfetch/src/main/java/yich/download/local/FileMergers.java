package yich.download.local;

public class FileMergers {
    public static FileMerger newFileMerger() {
        return new FileMerger(Config.DIR_COPY_TO, Config.DIR_GEN_TO)
                     .setTag(Config.MERGER_TAG)
                     .setSuffix(Config.MERGER_SUFFIX)
                     .setDelSrc(Config.MERGER_DEL_SRC);
    }

}
