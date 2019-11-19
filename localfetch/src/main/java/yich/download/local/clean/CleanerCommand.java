package yich.download.local.clean;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.picocli.PicoCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanerCommand extends PicoCommand<Integer> {
    final public static Logger logger = JUL.getLogger(CleanerCommand.class);

    @CommandLine.Option(names = {"-ca", "--cached"}, description = "Clean web cached video files")
    Boolean delCached = false;

    @CommandLine.Option(names = {"-co", "--collected"}, description = "Clean collected video files")
    Boolean delCollected = false;

    @CommandLine.Option(names = {"-c", "--all"}, description = "Clean all cached and collected video files")
    Boolean delAllSrc = false;

    @Override
    public Integer call() {
        int acc;
        try {
            acc = FileCleaners.newInstance(parameters()).start();
        } catch (Exception e) {
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }
        return acc;
    }

    @Override
    protected Class getClassType() {
        return CleanerCommand.class;
    }
}
