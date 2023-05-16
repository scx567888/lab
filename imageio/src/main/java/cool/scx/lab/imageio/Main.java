package cool.scx.lab.imageio;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Main class.</p>
 *
 * @author scx567888
 * @version 1.0.0
 */
public class Main {

    /**
     * <p>getImageWriter.</p>
     *
     * @param formatName a {@link java.lang.String} object
     * @return a {@link javax.imageio.ImageWriter} object
     */
    public static ImageWriter getImageWriter(String formatName) {
        var writerIterator = ImageIO.getImageWritersByFormatName(formatName.trim().toLowerCase());
        var writer = writerIterator.next();
        if (writer == null) {
            throw new IllegalArgumentException("未找到对应格式的 ImageWriter , formatName : " + formatName);
        }
        return writer;
    }

    /**
     * <p>progressiveJPEG.</p>
     *
     * @param sourceImageByte an array of {@link byte} objects
     * @return an array of {@link byte} objects
     * @throws java.io.IOException if any.
     */
    public static byte[] progressiveJPEG(byte[] sourceImageByte) throws IOException {
        //源文件
        var sourceImage = ImageIO.read(new ByteArrayInputStream(sourceImageByte));
        //图片 writer
        var jpegWriter = getImageWriter("jpeg");
        //参数
        var writeParam = jpegWriter.getDefaultWriteParam();
        //使用默认参数以启用渐进式图片
        writeParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
        //开始转换
        try (var b = new ByteArrayOutputStream(); var m = new MemoryCacheImageOutputStream(b)) {
            jpegWriter.setOutput(m);
            jpegWriter.write(null, new IIOImage(sourceImage, null, null), writeParam);
            jpegWriter.dispose();
            return b.toByteArray();
        }
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws java.io.IOException if any.
     */
    public static void main(String[] args) throws IOException {
        var sourceFile = Path.of("C:\\Users\\worker\\Pictures\\Wallpaper\\img (3).png");
        var targetFile = Path.of("xxx.jpeg");
        var imageByte = progressiveJPEG(Files.readAllBytes(sourceFile));
        Files.write(targetFile, imageByte);
    }

}
