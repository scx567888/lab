package cool.scx.lab.djc;

import jdk.jshell.JShell;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var str = """    
                    public class MyClass {
                        public static void main(String[] args) {
                          var str="1";
                            while(true){
                              str=str+str;
                              System.out.println("打印数据");
                            }
//                            System.out.println("打印数据");
//                            System.exit(1);
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
        jShell.eval(str);
        jShell.eval("MyClass.main(null)");

        System.out.println(byteArrayOutputStream.toString());
//        Class<?> myClass = DynamicJavaCompiler.compile("MyClass", str);
//        Method main = myClass.getDeclaredMethod("main", String[].class);
//        main.invoke(null, new Object[]{new String[]{}});
        System.out.println("主进程并不会退出!!!");

    }

}
