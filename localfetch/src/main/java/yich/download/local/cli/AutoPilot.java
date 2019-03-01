package yich.download.local.cli;

import yich.base.logging.JUL;
import yich.download.local.Config;
import yich.download.local.FileCollector;
import yich.download.local.FileMerger;
import yich.download.local.TSFileDetector;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

public class AutoPilot {
    final public static Logger logger = JUL.getLogger(AutoPilot.class);

    public static void main(String[] args) {
        String copy_src = Config.DOWNLOAD.getProperty("dir.copy.source");
        String copy_dst = Config.DOWNLOAD.getProperty("dir.copy.destination");
        String gen_dst = Config.DOWNLOAD.getProperty("dir.gen.destination");

        FileCollector collector = new FileCollector(Paths.get(copy_src), Paths.get(copy_dst));
        collector
                .setFormatDetector(new TSFileDetector())
                .setDelSrc(true)
                .start();

        System.out.println("Press enter to quit Collecting and to Merge...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        collector.close();

        FileMerger fileMerger = new FileMerger(Paths.get(copy_dst), Paths.get(gen_dst));
        fileMerger.merge(true);

    }
}
