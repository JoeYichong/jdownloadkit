package yich.download.test;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.Format;
import org.jcodec.common.JCodecUtil;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CodecTest {

    public static void main(String[] args) throws IOException, JCodecException {
//        int frameNumber = 5;
//        Picture picture = FrameGrab.getFrameFromFile(new File("D:\\TEMP\\chrome_temp\\sample\\f_009139"), frameNumber);
//
//        //for JDK (jcodec-javase)
//        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
//        ImageIO.write(bufferedImage, "png", new File("D:\\TEMP\\chrome_temp\\frame5.png"));

        Format format = JCodecUtil.detectFormat(new File("D:\\TEMP\\chrome_temp\\sample\\f_009139"));
        System.out.println(format.name());
        format = JCodecUtil.detectFormat(
                new File("C:\\Users\\birdonwire\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 3\\Cache\\f_00929c"));
        System.out.println(format.name());


    }

}
