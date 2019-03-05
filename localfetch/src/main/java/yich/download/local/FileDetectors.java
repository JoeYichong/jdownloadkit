package yich.download.local;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class FileDetectors {
    public static Predicate<Path> get(String type) {
        switch (type) {
            case "file" : return path -> Files.isRegularFile(path);
            case "ts" :
            case "ts1" : return new TSFileDetector();
            case "ts2" : return new TSFileDetector(true);

            default: return new TSFileDetector();
        }
    }
}
