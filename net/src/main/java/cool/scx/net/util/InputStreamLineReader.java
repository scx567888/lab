package cool.scx.net.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamLineReader {

    public static byte[] readMatch(InputStream inputStream, byte[] match) throws IOException {
        var buffer = new ByteArrayOutputStream();
        var i = 0;
        int b;

        while ((b = inputStream.read()) != -1) {
            buffer.write(b);
            if (b == match[i]) {
                i = i + 1;
                if (i == match.length) {
                    // 读取到完整的分隔符，返回之前的内容
                    return buffer.toByteArray();
                }
            } else {
                i = 0; // 重置匹配索引
            }
        }

        // 如果没有找到匹配，返回所有读取的内容
        throw new IllegalArgumentException("match not found");
    }

}
