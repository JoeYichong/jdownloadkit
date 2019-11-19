package yich.download.local.collect;

import yich.base.time.DefaultTimeInflater;
import yich.download.local.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class FileCollectors {
    public static FileCollector newInstance() {
        return new FileCollector(Config.DIR_COPY_FROM, Config.DIR_COPY_TO, FileDetectors.get(Config.FILE_DETECTOR))
                     .setInterval(Config.COLLECTOR_INTERVAL)
                     .setDelSrc(Config.COLLECTOR_DEL_SRC);
    }

    public static FileCollector newInstance(Map<String, String> options) {
        return setOptions(options, newInstance());
    }

    private static FileCollector setOptions(Map<String, String> options, FileCollector collector) {
        if (options.get("timeAfter") != null || options.get("timeBefore") != null) {
            var timePredicate =
                    new FileCreationTimePredicate("FileCreationTimePredicate", new DefaultTimeInflater());
            timePredicate.setTime(options.remove("timeAfter"), options.remove("timeBefore"));
            collector.addPredicate(timePredicate);
        }

//        paras.forEach((k, v) -> setOption(k, v, paras, collector));
        new ArrayList<>(options.entrySet())
                .forEach(entry -> setOption(entry.getKey(), entry.getValue(), options, collector));

//        System.out.println("FileCollectors ->" + options);
        return collector;
    }

    private static void setOption(String name, String value, Map<String, String> options, FileCollector collector) {
        switch (name) {
            case "delSrc":
                collector.setDelSrc(Boolean.parseBoolean(value)); break;
            case "alt":
                break;
            case "output":
                collector.setDst(value); break;
            case "input":
                collector.setSrc(value); break;
            default:
                return;
        }
        options.remove(name, value);
    }


}
