package cool.scx.core;

import cool.scx.common.util.$;
import cool.scx.common.util.ScxDateTimeFormatter;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

import java.time.LocalDateTime;

public class T {
    
    public static void main1(String[] args) {
        Scx scx = Scx.scx();

        ScxHttpServer httpServer = scx.createHttpServer();
        
        httpServer.requestHandler(c->{
            Context context = Vertx.currentContext();
            System.out.println(Thread.currentThread());
            c.response().end(LocalDateTime.now().format(ScxDateTimeFormatter.yyyy_MM_dd_HH_mm_ss_SSS));
//            $.sleep(1000);
        });

        httpServer.listen(8889);
        
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(c->{
            Context context = Vertx.currentContext();
            System.out.println(Thread.currentThread());
            c.response().end(LocalDateTime.now().format(ScxDateTimeFormatter.yyyy_MM_dd_HH_mm_ss_SSS));
            $.sleep(1000);
        });
        
        httpServer.listen(8889);
        
    }
    
}
