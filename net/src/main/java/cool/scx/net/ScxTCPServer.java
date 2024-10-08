package cool.scx.net;

import java.net.Socket;
import java.util.function.Consumer;

public interface ScxTCPServer {

    ScxTCPServer onConnect(Consumer<Socket> connectHandler);
    
    void start();

    void stop();

}
