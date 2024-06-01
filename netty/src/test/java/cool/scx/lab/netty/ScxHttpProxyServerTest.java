package cool.scx.lab.netty;

import cool.scx.lab.netty.http_proxy.ScxHttpProxyServer;
import org.testng.annotations.Test;

public class ScxHttpProxyServerTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {
        new ScxHttpProxyServer().start(16667);
    }

}
