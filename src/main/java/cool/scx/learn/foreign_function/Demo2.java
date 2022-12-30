package cool.scx.learn.foreign_function;

import cool.scx.config.ScxEnvironment;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static cool.scx.learn.foreign_function.Demo1.detectIfAnsiCapable;

public class Demo2 {

    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
        beforeTest();
        test1();
        var s=new ScxEnvironment(Demo2.class);

        //创建一个robot对象
        Robot robut=new Robot();
        //获取屏幕分辨率
        Dimension d=  Toolkit.getDefaultToolkit().getScreenSize();
        //打印屏幕分辨率
        System.out.println(d);
        //创建该分辨率的矩形对象
        Rectangle screenRect=new  Rectangle(d);
        //根据这个矩形截图
        BufferedImage bufferedImage=robut.createScreenCapture(screenRect);
        //保存截图
        var file=s.getTempPath("截图1.png");
        Files.createDirectories(file);
        ImageIO.write(bufferedImage,"png",file.toFile());
    }

    public static void beforeTest() {
        var b = detectIfAnsiCapable();
        System.out.println("支持 ansi : " + b);
    }

    public static void test1() {
        for (int i = 0; i < 256; i++) {
            var s = new Object[3];
            s[0] = new Ansi8BitColor(i);
            s[1] = new Ansi8BitBackground(255 - i);
            s[2] = "●★▲";
            if ((i + 1) % 8 == 0) {
                System.out.println(buildEnabled(s));
            } else {
                System.out.print(buildEnabled(s));
            }
        }
    }

    public interface AnsiElement {

        /**
         * <p>code.</p>
         *
         * @return the ANSI escape code
         */
        String ansiCode();

    }


    private static String buildEnabled(Object[] elements) {
        StringBuilder sb = new StringBuilder();
        boolean writingAnsi = false;
        boolean containsEncoding = false;
        for (var element : elements) {
            if (element instanceof AnsiElement) {
                containsEncoding = true;
                if (!writingAnsi) {
                    sb.append(ENCODE_START);
                    writingAnsi = true;
                } else {
                    sb.append(ENCODE_JOIN);
                }
                sb.append(((AnsiElement) element).ansiCode());
            } else {
                if (writingAnsi) {
                    sb.append(ENCODE_END);
                    writingAnsi = false;
                }
                sb.append(element);
            }
        }
        if (containsEncoding) {
            sb.append(writingAnsi ? ENCODE_JOIN : ENCODE_START);
            sb.append(RESET);
            sb.append(ENCODE_END);
        }
        return sb.toString();
    }

    /**
     * 合并字符
     */
    private static final String ENCODE_JOIN = ";";

    /**
     * 起始字符
     */
    private static final String ENCODE_START = "\033[";

    /**
     * 结束字符
     */
    private static final String ENCODE_END = "m";

    /**
     * 重置
     */
    private static final String RESET = "0;" + 39;

    public record Ansi8BitColor(int code) implements AnsiElement {

        public Ansi8BitColor {
            if (code < 0 || code > 255) {
                throw new IllegalArgumentException("Code must be between 0 and 255");
            }
        }

        @Override
        public String ansiCode() {
            return "38;5;" + this.code;
        }

    }

    public record Ansi8BitBackground(int code) implements AnsiElement {

        public Ansi8BitBackground {
            if (code < 0 || code > 255) {
                throw new IllegalArgumentException("Code must be between 0 and 255");
            }
        }

        @Override
        public String ansiCode() {
            return "48;5;" + this.code;
        }

    }
}
