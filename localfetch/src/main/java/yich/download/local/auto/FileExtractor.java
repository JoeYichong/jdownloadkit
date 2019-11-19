package yich.download.local.auto;

import yich.base.logging.JUL;
import yich.download.local.clean.FileCleaner;
import yich.download.local.collect.FileCollector;
import yich.download.local.merge.FileMerger;

import java.util.Scanner;
import java.util.logging.Logger;

public class FileExtractor {
//    final public static Logger logger = JUL.getLogger(FileExtractor.class);

    private FileCollector collector;
    private FileMerger merger;
    private FileCleaner cleaner;

    public FileExtractor() {
        super();
    }


    public FileCollector collector() {
        return collector;
    }

    public FileExtractor setCollector(FileCollector collector) {
        this.collector = collector;
        return this;
    }

    public FileMerger merger() {
        return merger;
    }

    public FileExtractor setMerger(FileMerger merger) {
        this.merger = merger;
        return this;
    }

    public FileCleaner cleaner() {
        return cleaner;
    }

    public FileExtractor setCleaner(FileCleaner cleaner) {
        this.cleaner = cleaner;
        return this;
    }

    public String start() {

        String re = null;
        if (this.collector != null) {
            this.collector.start();
            System.out.println("Press enter to quit Collecting...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            this.collector.close();
        }

        if (this.merger != null) {
            re = this.merger.start();
        }

        if (this.cleaner != null) {
            this.cleaner.start();
        }

        return re;
    }

}
