package yich.download.local.cli;

import yich.base.logging.JUL;
import yich.download.local.*;

import java.util.Scanner;
import java.util.logging.Logger;

public class AutoPilot {
    final public static Logger logger = JUL.getLogger(AutoPilot.class);

    private boolean delCached = false;

    private boolean delCollected = true;

    private String tag = null;

    private String suffix = null;

    private String output = null;

    private boolean alt = false;

    public AutoPilot() {
        super();
    }

    public AutoPilot(boolean delCached, boolean delCollected) {
        this.delCached = delCached;
        this.delCollected = delCollected;
    }

    public static AutoPilot get() {
        return new AutoPilot();
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

    public String getSuffix() {
        return suffix;
    }

    public AutoPilot setSuffix(String suffix) {
        this.suffix = suffix;
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
        FileCollector collector = FileCollectors.newFileCollector();
        collector.setFileDetector(alt ? FileDetectors.get("ts2") : null)
                 .setDelSrc(delCached)
                 .start();

        System.out.println("Press enter to quit Collecting...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        collector.close();

        return  FileMergers.newFileMerger()
                           .setDst(output)
                           .setTag(tag)
                           .setSuffix(suffix)
                           .merge(delCollected);
    }

}
