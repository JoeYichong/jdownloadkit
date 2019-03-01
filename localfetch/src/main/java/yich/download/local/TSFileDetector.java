package yich.download.local;

import java.nio.file.Path;
import java.util.function.Predicate;

public class TSFileDetector implements Predicate<Path> {
    @Override
    public boolean test(Path path) {
        return FormatDetector.isMPEG_TS(path);
    }
}
