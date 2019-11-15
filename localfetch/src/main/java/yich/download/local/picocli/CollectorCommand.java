package yich.download.local.picocli;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.collect.FileCollectors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectorCommand implements Callable<Future> {
    final public static Logger logger = JUL.getLogger(CollectorCommand.class);

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean input files")
    Boolean delSrc = false;

    @CommandLine.Option(names = {"--alt"}, description = "using alternative MPEG-TS file detecting method")
    Boolean alt = false;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input Directory")
    String input = null;

    @CommandLine.Option(names = {"-a", "--after"}, description = "Select files created after a specified time")
    String timeAfter = null;

    @CommandLine.Option(names = {"-b", "--before"}, description = "Select files created before a specified time")
    String timeBefore = null;

    private String toStr(Object value){
        return value == null ? null : String.valueOf(value);
    }

    private Map<String, String> parameters() throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Field[] fields = CollectorCommand.class.getDeclaredFields();
        String value;
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            value = toStr(fields[i].get(this));
            if (value != null) {
                map.put(fields[i].getName(), value);
            }
        }
//        System.out.println("Hello");
//        map.forEach((k, v) -> System.out.println(k + ":" + v));

        return map;
    }

    @Override
    public Future call() {
        try {
             return FileCollectors.newFileCollector()
                              .set(parameters())
                              .start();
//                     .setDelSrc(delSrc)
//                     .setSrc(input)
//                     .setDst(output)
//                     .start(alt);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }

    }


}
