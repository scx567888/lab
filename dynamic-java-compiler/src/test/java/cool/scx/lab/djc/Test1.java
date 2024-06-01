package cool.scx.lab.djc;

import cool.scx.lab.djc.DynamicJavaCompiler;
import cool.scx.lab.djc.SourceCode;

import java.lang.reflect.Method;

public class Test1 {
    public static void main(String[] args) throws Exception {

        var str = """
                import java.io.IOException;
                import java.nio.file.FileVisitResult;
                import java.nio.file.Files;
                import java.nio.file.Path;
                import java.nio.file.SimpleFileVisitor;
                import java.nio.file.attribute.BasicFileAttributes;
                import java.sql.SQLOutput;

                public class Java21 {

                    public static void main(String[] args)  throws IOException {
                            Files.walkFileTree(Path.of("C:/Users/worker/Projects"),new SimpleFileVisitor<>(){
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                System.out.println(file);
                                return super.visitFile(file, attrs);
                            }
                        });
                    }

                }""";
        var str2 = """
                package cool.scx;
                                
                public class Animal {
                }
                """;
        var str1 = """
                            package cool.scx;
                            public class Cat extends Animal{
                                    
                                    public static Object aaa(){
                                     return System.out;
                                    }                            
                            
                                            }
                """;

        for (int i = 0; i < 10; i++) {
            var java211 = DynamicJavaCompiler
                    .compile(new SourceCode("cool.scx.Cat", str1), new SourceCode("cool.scx.Animal", str2));
            Class<?> aClass1 = java211.get(0);

            Method aaa = aClass1.getMethod("aaa");

            Object invoke = aaa.invoke(null);
            System.out.println(invoke);
            System.out.println(aClass1.hashCode());
            System.out.println(System.out);
        }


        Class<?> aClass = Class.forName("cool.scx.Cat");
        System.out.println();

    }
}
