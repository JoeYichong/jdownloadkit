package yich.download.local.clean;


import yich.download.local.Config;

import java.util.Map;

public class FileCleaners {
    public static FileCleaner newInstance(){
        return FileCleaner.getInstance();
    }

    public static FileCleaner newInstance(Map<String, String> options) {
        return setOptions(options, newInstance());
    }

    private static FileCleaner setOptions(Map<String, String> options, FileCleaner cleaner) {
        options.forEach((k, v) -> {
            if ("true".equals(v))
                setOptions0(k, v, cleaner);
        });
        return cleaner;
    }

    private static void setOptions0(String name, String value, FileCleaner cleaner) {
        switch (name) {
            case "delCache":
                cleaner.addPath(Config.DIR_COPY_FROM);
                break;
            case "delCollected":
                cleaner.addPath(Config.DIR_COPY_TO);
                break;
            case "delAll":
                cleaner.addPath(Config.DIR_COPY_FROM);
                cleaner.addPath(Config.DIR_COPY_TO);
                break;
        }
    }


}
