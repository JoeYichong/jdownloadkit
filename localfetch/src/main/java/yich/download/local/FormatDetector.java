package yich.download.local;

import org.jcodec.common.JCodecUtil;

import java.nio.file.Path;

public class FormatDetector {
    public static boolean isMPEG_TS(Path path) {
        try {
            return "MPEG_TS".equals(JCodecUtil.detectFormat(path.toFile()).name());
        } catch (Exception e) {
            return false;
        }
    }

}
