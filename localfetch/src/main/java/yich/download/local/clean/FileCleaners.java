package yich.download.local.clean;


import yich.download.local.Config;

import java.util.ArrayList;
import java.util.Map;

public class FileCleaners {
    public static FileCleaner newInstance(){
        return FileCleaner.getInstance();
    }

    public static FileCleaner newInstance(Map<String, String> options) {
        return setOptions(options, newInstance());
    }

    private static FileCleaner setOptions(Map<String, String> options, FileCleaner cleaner) {
//        options.forEach((k, v) -> {
//            if ("true".equals(v))
//                setOption(k, v, cleaner);
//        });

        new ArrayList<>(options.entrySet())
                .forEach(entry -> {
                    if ("true".equals(entry.getValue()))
                       setOption(entry.getKey(), entry.getValue(), options, cleaner);
        });

//        System.out.println("FileCleaners ->" + options);

        return cleaner;
    }

    private static void setOption(String name, String value, Map<String, String> options, FileCleaner cleaner) {
        switch (name) {
            case "delCached":
                cleaner.addPath(Config.DIR_CACHED);
                break;
            case "delCollected":
                cleaner.addPath(Config.DIR_COLLECTED);
                break;
            case "delAllSrc":
                cleaner.addPath(Config.DIR_CACHED);
                cleaner.addPath(Config.DIR_COLLECTED);
                break;
            default:
                return;
        }
        options.remove(name, value);
    }


}
