package yich.download.local.auto;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.picocli.PicoCommand;

import java.util.logging.Logger;

public class ExtractorCommand extends PicoCommand<String> {
    final public static Logger logger = JUL.getLogger(ExtractorCommand.class);

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean all input files")
    Boolean delAllSrc = false;

    @CommandLine.Option(names = {"-ca", "--cleanCached"}, description = "Clean Cached files")
    Boolean delCached = false;

    @CommandLine.Option(names = {"-co", "--cleanCollected"}, description = "Clean Collected files")
    Boolean delCollected = false;

    @CommandLine.Option(names = {"-t", "--tag"}, description = "File name tag")
    String tag = null;

    @CommandLine.Option(names = {"-s", "--suffix"}, description = "File suffix")
    String suffix = null;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"--alt"}, description = "using alternative file detecting method")
    Boolean alt = false;

    @CommandLine.Option(names = {"-a", "--after"}, description = "Select files created after a specified time")
    String timeAfter = null;

    @CommandLine.Option(names = {"-b", "--before"}, description = "Select files created before a specified time")
    String timeBefore = null;



    @Override
    public String call() {
        try {
            return FileExtractors.newInstance(parameters()).start();
        } catch (Exception e) {
            System.out.println("** Error: " + e.getMessage());
            logger.severe("** Error: " + e.getMessage());
            return null;
        }

    }

    @Override
    protected Class getClassType() {
        return ExtractorCommand.class;
    }
}
