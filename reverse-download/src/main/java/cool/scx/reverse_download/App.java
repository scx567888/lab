package cool.scx.reverse_download;

import cool.scx.config.ScxEnvironment;
import cool.scx.common.util.ArrayUtils;
import cool.scx.common.util.FileUtils;
import cool.scx.common.ansi.Ansi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        ScxEnvironment scxEnvironment = new ScxEnvironment(App.class);
        Path path = scxEnvironment.appRootPath();
        File[] files = path.toFile().listFiles();
        if (files == null) {
            Ansi.ansi().red("未检测到任何文件 !!!").println();
            return;
        }
        for (File file : files) {
            try {
                if (!file.isFile()) {
                    continue;
                }
                var a = FileUtils.getFileNameWithoutExtension(file.getName());
                var b = FileUtils.getExtension(file.getName());
                if ("apk".equals(b)) {
                    Ansi.ansi().blue("检测到安装包 " + file.getName() + " 正在转换...").println();
                    byte[] allBytes = Files.readAllBytes(file.toPath());
                    ArrayUtils.reverse(allBytes);
                    var newName = a + ".kpa";
                    Files.write(path.resolve(Path.of("./" + newName)), allBytes);
                    Ansi.ansi().green("转换完成 " + newName).println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
