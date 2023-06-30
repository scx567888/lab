package cool.scx.lab.foreign_function;

import cool.scx.config.ScxEnvironment;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Demo2 {

    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
        try {
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
//        robut.createMultiResolutionScreenCapture()
            BufferedImage bufferedImage=robut.createScreenCapture(screenRect);
            //保存截图
            var file=s.getTempPath("截图1.png");
            Files.createDirectories(file);
            ImageIO.write(bufferedImage,"png",file.toFile());
        }catch (Throwable e){
            System.out.println();
        }

    }

}
