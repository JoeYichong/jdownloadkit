package yich.download.local.merge;

import yich.download.local.Config;

import java.util.Map;

public class FileMergers {
    public static FileMerger newInstance() {
        return new FileMerger(Config.DIR_COPY_TO, Config.DIR_GEN_TO)
                .setTag(Config.MERGER_TAG)
                .setSuffix(Config.MERGER_SUFFIX)
                .setDelSrc(Config.MERGER_DEL_SRC);
    }

    public static FileMerger newInstance(Map<String, String> options) {
        return setOptions(options, newInstance());
    }

    private static FileMerger setOptions(Map<String, String> options, FileMerger merger) {
        options.forEach((k, v) -> setOptions0(k, v, merger));
        return merger;
    }

    private static void setOptions0(String name, String value, FileMerger merger) {
        switch (name) {
            case "tag":
                merger.setTag(value);
                break;
            case "suffix":
                merger.setSuffix(value);
                break;
            case "delSrc":
                merger.setDelSrc(Boolean.parseBoolean(value));
                break;
            case "output":
                merger.setDst(value);
                break;
            case "input":
                merger.setSrc(value);
                break;
        }
    }
}