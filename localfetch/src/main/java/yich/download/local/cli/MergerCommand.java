package yich.download.local.cli;

import picocli.CommandLine;

import yich.base.logging.JUL;
import yich.download.local.FileMergers;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MergerCommand implements Callable<String> {
    final public static Logger logger = JUL.getLogger(MergerCommand.class);

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean input files")
    boolean delSrc = false;

    @CommandLine.Option(names = {"-t", "--tag"}, description = "File name tag")
    String tag = null;

    @CommandLine.Option(names = {"-s", "--suffix"}, description = "File suffix")
    String suffix = null;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input Directory")
    String input = null;


    @Override
    public String call() {
        try {
            return FileMergers.newFileMerger()
                              .setTag(tag)
                              .setSuffix(suffix)
                              .setSrc(input)
                              .setDst(output)
                              .merge(delSrc);
        } catch (Exception e) {
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }
    }

}
