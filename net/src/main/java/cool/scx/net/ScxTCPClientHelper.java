package cool.scx.net;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.net.Socket;
import java.security.KeyStore;

public class ScxTCPClientHelper {

    public static Socket createSocket(TLS tls) {

        try {

            //创建 TLS 的 Socket
            if (tls != null && tls.enabled()) {

                // 证书存储器
                var keyStore = KeyStore.getInstance(tls.path().toFile(), tls.password().toCharArray());

                // 初始化 TrustManagerFactory
                var trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
                trustManagerFactory.init(keyStore);

                // 创建 SSLContext
                var sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

                var socketFactory = sslContext.getSocketFactory();
                return socketFactory.createSocket();

            } else {
                //创建 普通 的 Socket
                return new Socket();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
