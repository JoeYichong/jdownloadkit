package yich.download.local.collect;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.picocli.MyCommand;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectorCommand extends MyCommand<Future> {
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



    @Override
    public Future call() {
        try {
             return FileCollectors.newInstance(parameters())
                                  .start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }

    }

    @Override
    protected Class getClassType() {
        return CollectorCommand.class;
    }

//    public static void main(String[] args) throws IllegalAccessException {
//        new CollectorCommand().parameters().forEach((k, v) -> System.out.println(k + ":" + v));
//    }

}
