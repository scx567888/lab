package cool.scx.lab.djc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyClass {
    public static String main(String[] args) {
        try {
            Files.createFile(Path.of("C:\\Users\\worker\\Desktop\\aaa"));
        } catch (IOException e) {
            System.out.println(e);
        }
        return "123";
    }
}
