package yich.download.local.clean;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.picocli.MyCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanerCommand extends MyCommand<Integer> {
    final public static Logger logger = JUL.getLogger(CleanerCommand.class);

    @CommandLine.Option(names = {"-ca", "--cached"}, description = "Clean web cached video files")
    Boolean delCache = false;

    @CommandLine.Option(names = {"-co", "--collected"}, description = "Clean collected video files")
    Boolean delCollected = false;

    @CommandLine.Option(names = {"-a", "--all"}, description = "Clean all cached and collected video files")
    Boolean delAll = false;

    @Override
    public Integer call() {
        int acc = 0;
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
