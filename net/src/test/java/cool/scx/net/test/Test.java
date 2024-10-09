package cool.scx.net.test;

import cool.scx.http.ScxHttpServer;
import cool.scx.http.ScxHttpServerOptions;
import cool.scx.net.ScxTCPServer;
import cool.scx.net.ScxTCPServerImpl;
import cool.scx.net.ScxTCPServerOptions;
import cool.scx.net.TLS;
import cool.scx.net.http.ScxHttpServerImpl;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        TLS ttt = new TLS()
                .path(Path.of("C:\\Users\\scx\\Downloads\\keystore.jks")).password("123456");
        ttt=null;
        var s= new ScxHttpServerOptions().port(8080);
        s.tls(ttt);
        ScxHttpServer server = new ScxHttpServerImpl(s);
        server.requestHandler(c -> {
            String string = c.body().asString();
            c.response().send("123");
        });
        server.start();
    }
}
