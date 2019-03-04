package yich.download.local.cli;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.Config;
import yich.download.local.FileCollector;
import yich.download.local.TSFileDetector;

import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectorCommand implements Callable<Future> {
    final public static Logger logger = JUL.getLogger(CollectorCommand.class);

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean input files")
    boolean delSrc = false;

    @CommandLine.Option(names = {"--alt"}, description = "using alternative MPEG-TS file detecting method")
    boolean alter = false;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input Directory")
    String input = null;


    @Override
    public Future call() {
        String copy_src = Config.DOWNLOAD.getProperty("dir.copy.source");
        String copy_dst = Config.DOWNLOAD.getProperty("dir.copy.destination");
        try {
            return new FileCollector(Paths.get(copy_src), Paths.get(copy_dst))
                    .setFormatDetector(alter ? new TSFileDetector(true) : new TSFileDetector())
                    .setDelSrc(delSrc)
                    .setSrc(input)
                    .setDst(output)
                    .start();
        } catch (Exception e) {
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }

    }
}
