package yich.download.test;

import org.jcodec.common.JCodecUtil;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class FileCopyTest {
    public static String src = "C:\\Users\\birdonwire\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 3\\Cache";
    public static String dst = "D:\\TEMP\\chrome_temp\\dst\\";

    public static boolean isMPEG_Ts(Path path) {
        try {
//            return "MPEG_TS".equals(JCodecUtil
//                    .detectFormatChannel(Files.newByteChannel(path, StandardOpenOption.READ)).name());
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            // System.out.println(path.getFileName() + " creationTime: " + attr.creationTime());
            return "MPEG_TS".equals(JCodecUtil
                    .detectFormat(path.toFile()).name());

        } catch (Exception e) {
            //throw new RuntimeException(e);

            // System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(src))) {
                    paths
                            .filter(Files::isRegularFile)
                            .filter(FileCopyTest::isMPEG_Ts)
//                            .sorted(Comparator.comparing(path -> {
//                                        try {
//                                            return Files.readAttributes(path, BasicFileAttributes.class)
//                                                                .creationTime().toMillis();
//                                        } catch (IOException e) {
//                                            throw new RuntimeException(e);
//                                        }
//                                })
//                             )
                            .forEach(path -> {
                                try {
                                    long milli =
                                                 Files.readAttributes(path, BasicFileAttributes.class)
                                                      .creationTime().toMillis();
                                    Files.copy(path, Paths.get(dst + milli));
                                    System.out.println(dst + path.getFileName());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

        }

    }

}
