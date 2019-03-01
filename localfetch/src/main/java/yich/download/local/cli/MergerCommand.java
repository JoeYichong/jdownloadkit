package yich.download.local.cli;

import picocli.CommandLine;

import yich.download.local.Config;
import yich.download.local.FileMerger;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class MergerCommand implements Callable<FileMerger> {
    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean source files")
    boolean delSrc = false;

    @CommandLine.Option(names = {"-t", "--tag"}, description = "File name tag")
    String tag = "";

    @Override
    public FileMerger call() throws Exception {
        String copy_dst = Config.DOWNLOAD.getProperty("dir.copy.destination");
        String gen_dst = Config.DOWNLOAD.getProperty("dir.gen.destination");
        FileMerger fileMerger = new FileMerger(Paths.get(copy_dst), Paths.get(gen_dst));
        fileMerger.setTag(tag)
                  .merge(delSrc);
        return fileMerger;
    }
}
