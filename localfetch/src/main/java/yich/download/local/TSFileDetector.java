package yich.download.local;

import java.nio.file.Path;
import java.util.function.Predicate;

public class TSFileDetector implements Predicate<Path> {
    private boolean alter = false;

    public TSFileDetector() {
        super();
    }

    public TSFileDetector(boolean alter) {
        this.alter = alter;
        System.out.println("alter: " + this.alter);
    }

    public boolean isAlter() {
        return alter;
    }

    public TSFileDetector setAlter(boolean alter) {
        this.alter = alter;
        return this;
    }

    @Override
    public boolean test(Path path) {
        return alter ? FormatDetector.isMPEG_TS_B(path) : FormatDetector.isMPEG_TS(path);
    }
}
