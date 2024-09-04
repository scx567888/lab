package cool.scx.lab.djc;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var str = """    
                   
                     import java.io.IOException;
                     import java.nio.file.Files;
                     import java.nio.file.Path;
                     
                     public class MyClass {
                         public static String main(String[] args) {
                             try {
                                 Files.createFile(Path.of("C:\\\\Users\\\\worker\\\\Desktop\\\\aaa"));
                             } catch (IOException e) {
                                 System.out.println(e);
                             }
                             return "123";
                         }
                     }
                """;

      
        var byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        printStream.println("");
        // 将System.out的输出重定向到PrintStream中
        JShell jShell=JShell.builder()
//                .out(printStream)
                .build();
        List<SnippetEvent> eval1 = jShell.eval(str);
        List<SnippetEvent> eval = jShell.eval("MyClass.main(null)");

        System.out.println(byteArrayOutputStream.toString());
//        Class<?> myClass = DynamicJavaCompiler.compile("MyClass", str);
//        Method main = myClass.getDeclaredMethod("main", String[].class);
//        main.invoke(null, new Object[]{new String[]{}});
        System.out.println("主进程并不会退出!!!");

    }

}
