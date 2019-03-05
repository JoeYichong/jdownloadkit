package yich.download.local.picocli;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.*;

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
        try {
            return FileCollectors.newFileCollector()
                    .setFileDetector(alter ? FileDetectors.get("ts2") : null)
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
