package yich.download.local.auto;

import yich.download.local.clean.FileCleaners;
import yich.download.local.collect.FileCollectors;
import yich.download.local.merge.FileMergers;

import java.util.Map;

public class FileExtractors {
    public static FileExtractor newInstance() {
        return new FileExtractor();
    }

    public static FileExtractor newInstance(Map<String, String> options) {
        return setOptions(options, newInstance());
    }

    private static FileExtractor setOptions(Map<String, String> options, FileExtractor extractor) {

        extractor.setCleaner(FileCleaners.newInstance(options));
        extractor.setMerger(FileMergers.newInstance(options));
        extractor.setCollector(FileCollectors.newInstance(options));

        return extractor;
    }


}
