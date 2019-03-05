package yich.download.local.picocli;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.AutoPilot;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class AutoCommand implements Callable<String> {
    final public static Logger logger = JUL.getLogger(AutoCommand.class);

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean all input files")
    boolean delAllSrc = false;

    @CommandLine.Option(names = {"-ca", "--cleanCached"}, description = "Clean Cached files")
    boolean delCached = false;

    @CommandLine.Option(names = {"-co", "--cleanCollected"}, description = "Clean Collected files")
    boolean delCollected = false;

    @CommandLine.Option(names = {"-t", "--tag"}, description = "File name tag")
    String tag = null;

    @CommandLine.Option(names = {"-s", "--suffix"}, description = "File suffix")
    String suffix = null;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"--alt"}, description = "using alternative MPEG-TS file detecting method")
    boolean alt = false;


    @Override
    public String call() {
        try {
            return AutoPilot.get()
                    .setDelCached(delAllSrc || delCached)
                    .setDelCollected(delAllSrc || delCollected)
                    .setAlt(alt)
                    .setOutput(output)
                    .setTag(tag)
                    .setSuffix(suffix)
                    .autoRun();
        } catch (Exception e) {
            System.out.println("** Error: " + e.getMessage());
            logger.severe("** Error: " + e.getMessage());
            return null;
        }

    }
}
