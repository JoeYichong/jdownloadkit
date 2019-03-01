package yich.download.local.cli;

import picocli.CommandLine;
import yich.base.logging.JUL;
import yich.download.local.Config;
import yich.download.local.FileCleaner;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
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
        FileCleaner cleaner = new FileCleaner();
        cleaner.addPredicate(Files::isRegularFile);
        if (delCache || delAll) {
            num = cleaner.clean(Paths.get(Config.DOWNLOAD.getProperty("dir.copy.source")));
            acc += num;
            System.out.println("** Cleaner: " + num + " Cached Files has been Cleaned.");
            logger.info("** Cleaner: " + num + " Cached Files has been Cleaned.");
        }
        if (delCollected || delAll) {
            num = cleaner.clean(Paths.get(Config.DOWNLOAD.getProperty("dir.copy.destination")));
            acc += num;
            System.out.println("** Cleaner: " + num + " Collected Files has been Cleaned.");
            logger.info("** Cleaner: " + num + " Collected Files has been Cleaned.");
        }
        return acc;
    }
}
