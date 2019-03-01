package yich.download.local.cli;

import picocli.CommandLine;
import yich.download.local.Config;
import yich.download.local.FileCollector;
import yich.download.local.TSFileDetector;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class CollectorCommand implements Callable<FileCollector> {
    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean source files")
    boolean delSrc = false;

    @Override
    public FileCollector call() throws Exception {
        String copy_src = Config.DOWNLOAD.getProperty("dir.copy.source");
        String copy_dst = Config.DOWNLOAD.getProperty("dir.copy.destination");
        FileCollector collector = new FileCollector(Paths.get(copy_src), Paths.get(copy_dst));
        collector.setFormatDetector(new TSFileDetector())
                 .setDelSrc(delSrc)
                 .start();
        return collector;
    }
}
