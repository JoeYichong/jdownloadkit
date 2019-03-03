package yich.download.local.cli;

import picocli.CommandLine;

import java.util.concurrent.Callable;

public class AutoCommand implements Callable<String> {
    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean all source files")
    boolean delAllSrc = false;

    @CommandLine.Option(names = {"-ca", "--cleanCached"}, description = "Clean Cached files")
    boolean delCached = false;

    @CommandLine.Option(names = {"-co", "--cleanCollected"}, description = "Clean Collected files")
    boolean delCollected = false;

    @CommandLine.Option(names = {"-t", "--tag"}, description = "File name tag")
    String tag = "";

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"--alt"}, description = "using alternative MPEG-TS file detecting method")
    boolean alt = false;


    @Override
    public String call() {
        // System.out.println("alt: " + alt);
        return new AutoPilot(delAllSrc || delCached, delAllSrc || delCollected)
                .setAlt(alt)
                .setOutput(output)
                .setTag(tag)
                .autoRun();
    }
}
