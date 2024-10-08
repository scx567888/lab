package cool.scx.net;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.net.ServerSocket;
import java.security.KeyStore;

public class ScxTCPServerHelper {

    public static ServerSocket createServerSocket(TLS tls) {

        try {

            //创建 TLS 的 ServerSocket
            if (tls != null && tls.enabled()) {

                // 证书存储器
                var keyStore = KeyStore.getInstance(tls.path().toFile(), tls.password().toCharArray());

                // 初始化密钥管理器工厂
                var keyManagerFactory = KeyManagerFactory.getInstance("PKIX");
                keyManagerFactory.init(keyStore, tls.password().toCharArray());

                // 创建 SSLContext
                var sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

                var serverSocketFactory = sslContext.getServerSocketFactory();
                return serverSocketFactory.createServerSocket();

            } else {
                //创建 普通 的 ServerSocket
                return new ServerSocket();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
