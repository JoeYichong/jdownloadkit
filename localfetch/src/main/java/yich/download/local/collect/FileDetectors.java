package yich.download.local.collect;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileDetectors {
    public static FileDetector<Path> get(String type) {
        switch (type) {
            case "file" : return FileDetector.of((Path path) -> Files.isRegularFile(path));
            case "ts" :  return new TSFileDetector(false);
            case "ts2" : return new TSFileDetector(true);

            default: return new TSFileDetector();
        }
    }
}
