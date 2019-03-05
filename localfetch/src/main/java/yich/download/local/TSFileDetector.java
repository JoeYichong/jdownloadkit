package yich.download.local;

import org.jcodec.common.JCodecUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

public class TSFileDetector implements Predicate<Path> {
    private boolean alter = false;

    public TSFileDetector() {
        super();
    }

    public TSFileDetector(boolean alter) {
        this.alter = alter;
        // System.out.println("alt: " + this.alter);
    }

    public static boolean isMPEG_TS(Path path) {
        try {
            return "MPEG_TS".equals(JCodecUtil.detectFormat(path.toFile()).name());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMPEG_TS_B(Path path) {
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            byte[] arr = new byte[2];
            if (fis.read(arr) == 2 &&
                    (int) arr[0] == 71 && (int) arr[1] == 64){
                return true;
            }
        } catch (IOException e) {
        }
        return false;
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
        return alter ? TSFileDetector.isMPEG_TS_B(path) : TSFileDetector.isMPEG_TS(path);
    }
}
