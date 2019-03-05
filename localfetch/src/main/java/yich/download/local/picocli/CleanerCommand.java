package yich.download.local.picocli;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.Config;
import yich.download.local.FileCleaner;


import java.nio.file.Files;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanerCommand implements Callable<Integer> {
    final public static Logger logger = JUL.getLogger(CleanerCommand.class);

    @CommandLine.Option(names = {"-ca", "--cached"}, description = "Clean web cached video files")
    boolean delCache = false;

    @CommandLine.Option(names = {"-co", "--collected"}, description = "Clean collected video files")
    boolean delCollected = false;

    @CommandLine.Option(names = {"-a", "--all"}, description = "Clean all cached and collected video files")
    boolean delAll = false;

    @Override
    public Integer call() {
        int num, acc = 0;
        FileCleaner cleaner = FileCleaner.getInstance();
        cleaner.addPredicate(Files::isRegularFile);
        try {
            if (delCache || delAll) {
                num = cleaner.clean(Config.DIR_COPY_FROM);
                acc += num;
                System.out.println("** Cleaner: " + num + " Cached Files has been Cleaned.");
                logger.info("** Cleaner: " + num + " Cached Files has been Cleaned.");
            }
            if (delCollected || delAll) {
                num = cleaner.clean(Config.DIR_COPY_TO);
                acc += num;
                System.out.println("** Cleaner: " + num + " Collected Files has been Cleaned.");
                logger.info("** Cleaner: " + num + " Collected Files has been Cleaned.");
            }
        } catch (Exception e) {
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }
        return acc;
    }
}
