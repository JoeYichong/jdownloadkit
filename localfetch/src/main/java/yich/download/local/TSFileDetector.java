package yich.download.local;

import org.jcodec.common.JCodecUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class TSFileDetector extends FileDetector<Path> {
    private boolean isTS2 = false;

    public TSFileDetector() {
        super();
    }

    public TSFileDetector(boolean isTS2) {
        this.isTS2 = isTS2;
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


    public boolean isTS2() {
        return isTS2;
    }

    public TSFileDetector setTS2(boolean TS2) {
        this.isTS2 = TS2;
        return this;
    }

    @Override
    public void alt() {
        this.isTS2 = !this.isTS2;
        // System.out.print("<TSFileDetector: " + isTS2 + ">");
    }

    @Override
    public boolean test(Path path) {
        return isTS2 ? TSFileDetector.isMPEG_TS_B(path) : TSFileDetector.isMPEG_TS(path);
    }

}
