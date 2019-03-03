package yich.download.local.cli;

import yich.base.logging.JUL;
import yich.download.local.Config;
import yich.download.local.FileCollector;
import yich.download.local.FileMerger;
import yich.download.local.TSFileDetector;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

public class AutoPilot {
    final public static Logger logger = JUL.getLogger(AutoPilot.class);

    private boolean delCached = false;

    private boolean delCollected = false;

    private String tag = "";

    private String output = null;

    private boolean alt = false;

    public AutoPilot() {
        super();
    }

    public AutoPilot(boolean delCached, boolean delCollected) {
        this.delCached = delCached;
        this.delCollected = delCollected;
    }

    public boolean isDelCached() {
        return delCached;
    }

    public AutoPilot setDelCached(boolean delCached) {
        this.delCached = delCached;
        return this;
    }

    public boolean isDelCollected() {
        return delCollected;
    }

    public AutoPilot setDelCollected(boolean delCollected) {
        this.delCollected = delCollected;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public AutoPilot setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getOutput() {
        return output;
    }

    public AutoPilot setOutput(String output) {
        this.output = output;
        return this;
    }

    public boolean isAlt() {
        return alt;
    }

    public AutoPilot setAlt(boolean alt) {
        this.alt = alt;
        return this;
    }

    public String autoRun() {
        String copy_src = Config.DOWNLOAD.getProperty("dir.copy.source");
        String copy_dst = Config.DOWNLOAD.getProperty("dir.copy.destination");
        String gen_dst = Config.DOWNLOAD.getProperty("dir.gen.destination");

        FileCollector collector = new FileCollector(Paths.get(copy_src), Paths.get(copy_dst));
        collector
                .setFormatDetector(new TSFileDetector(alt))
                .setDelSrc(delCached)
                .start();

        System.out.println("Press enter to quit Collecting...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        collector.close();

        FileMerger fileMerger = new FileMerger(Paths.get(copy_dst), Paths.get(gen_dst));
        if (output != null) {
            Path path = Paths.get(output);
            if (Files.isDirectory(path)) {
                fileMerger.setDst(path);
            }
        }
        return fileMerger
                .setTag(tag)
                .merge(delCollected);
    }

}
