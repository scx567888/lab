package cool.scx.lab.djc;


import cool.scx.lab.djc.DynamicJavaCompiler;
import cool.scx.lab.djc.SourceCode;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class TestModule {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        run();
    }


    public static String run() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var s = """
                import java.io.PrintStream;

                public class System {
                    public static void exit(int status) {
                        java.lang.System.out.println("并没有效果");
                        java.lang.System.out.println("并没有效果");
                    }

                    public static final PrintStream out =java.lang.System.out ;


                }

                                """;

        var code = "public class MyTest {\n" +
                "    \n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"123\");\n" +
                "  System.exit(1);"+
                "    }\n" +
                "    \n" +
                "}\n";
        var myTests = DynamicJavaCompiler.compile(new SourceCode("MyTest", code), new SourceCode("java.lang.System", s));
        for (Class<?> myTest : myTests) {
            if (myTest.getName().equals("MyTest")) {
                var methods = myTest.getDeclaredMethod("main", String[].class);

                methods.invoke(null, new Object[]{new String[]{}});

                return myTest.getName();
            }
        }
        return null;

    }

    public static void main1(String[] args) throws SocketException, UnknownHostException {
        List<InetAddress> allAddresses = Stream.concat(NetworkInterface.networkInterfaces().flatMap(NetworkInterface::inetAddresses), Stream.of(InetAddress.getLocalHost())).filter((c) -> {
            return !c.isLoopbackAddress();
        }).toList();
        String[] ipv4AddressList = (String[]) allAddresses.stream().filter((c) -> {
            return c instanceof Inet4Address;
        }).map(InetAddress::getHostAddress).distinct().toArray((x$0) -> {
            return new String[x$0];
        });
        String[] ipv6AddressList = (String[]) allAddresses.stream().filter((c) -> {
            return c instanceof Inet6Address;
        }).map(InetAddress::getHostAddress).distinct().toArray((x$0) -> {
            return new String[x$0];
        });
        System.exit(0);
        System.out.println(Arrays.toString(ipv4AddressList));
        System.out.println(Arrays.toString(ipv6AddressList));
//        var s=new FileOutputStream();
//        s.write();
    }

}
