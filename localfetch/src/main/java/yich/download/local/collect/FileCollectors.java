package yich.download.local.collect;

import yich.base.time.DefaultTimeInflater;
import yich.download.local.Config;

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

    private static FileCollector setOptions(Map<String, String> paras, FileCollector collector) {
        if (paras.get("timeAfter") != null || paras.get("timeBefore") != null) {
            var timePredicate =
                    new FileCreationTimePredicate("FileCreationTimePredicate", new DefaultTimeInflater());
            timePredicate.setTime(paras.remove("timeAfter"), paras.remove("timeBefore"));
            collector.getFileDetector().addPredicate(timePredicate);
        }
        paras.forEach((k, v) -> setOptions0(k, v, collector));

        return collector;
    }

    private static void setOptions0(String name, String value, FileCollector collector) {
        switch (name) {
            case "delSrc":
                collector.setDelSrc(Boolean.parseBoolean(value)); break;
            case "alt": break;
            case "output":
                collector.setDst(value); break;
            case "input":
                collector.setSrc(value); break;
        }

    }


}
