package cool.scx.cocos_decoder;

import java.util.ArrayList;
import java.util.Arrays;

/// 适用于 cocos 的 uuid 工具
public class CocosUUIDUtils {

    private static final String BASE64_KEYS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // decoded value indexed by base64 char code
    private static final int[] BASE64_VALUES = initBASE64_VALUES(BASE64_KEYS);

    private static final String separator = "@";

    private static final char[] HexChars = "0123456789abcdef".toCharArray();

    private static final int[] HexMap = initHexMap(HexChars);

    /// 将 标准 UUID 压缩成 cocos UUID
    public static String compressUUID(String uuid) {
        var strs = uuid.split("@");
        var uuidPart = strs[0];
        if (uuidPart.length() != 36) {
            return uuidPart;
        }

        // 去除 "-"，得到 32 个十六进制字符
        String hex = uuid.replace("-", "");

        // 结果字符数组，前两个字符保留原样
        char[] base64 = new char[22];
        base64[0] = hex.charAt(0);
        base64[1] = hex.charAt(1);

        // 从第3个字符开始，每3个 hex -> 2个 base64 字符
        for (int i = 2, j = 2; i < 32; i += 3) {
            int left = HexMap[hex.charAt(i)];
            int mid = HexMap[hex.charAt(i + 1)];
            int right = HexMap[hex.charAt(i + 2)];

            base64[j++] = BASE64_KEYS.charAt((left << 2) | (mid >> 2));
            base64[j++] = BASE64_KEYS.charAt(((mid & 3) << 4) | right);
        }

        return uuid.replace(uuidPart, new String(base64));
    }

    /// 将 cocos 的 UUID 还原成 标准 UUID
    /// [源地址](https://github.com/cocos/cocos-engine/blob/v3.8.7/cocos/core/utils/decode-uuid.ts)
    public static String decompressUUID(String base64) {
        var strs = base64.split(separator);
        var uuid = strs[0];
        if (uuid.length() != 22) {
            return base64;
        }

        var UuidTemplate = createUuidTemplate();
        var Indices = createIndices(UuidTemplate);

        UuidTemplate[0] = base64.charAt(0);
        UuidTemplate[1] = base64.charAt(1);

        for (int i = 2, j = 2; i < 22; i += 2) {
            var lhs = BASE64_VALUES[base64.charAt(i)];
            var rhs = BASE64_VALUES[base64.charAt(i + 1)];
            UuidTemplate[Indices[j++]] = HexChars[lhs >> 2];
            UuidTemplate[Indices[j++]] = HexChars[((lhs & 3) << 2) | rhs >> 4];
            UuidTemplate[Indices[j++]] = HexChars[rhs & 0xF];
        }

        return base64.replace(uuid, new String(UuidTemplate));

    }

    public static char[] createUuidTemplate() {
        var UuidTemplate = new char[36];
        Arrays.fill(UuidTemplate, '0');
        UuidTemplate[8] = '-';
        UuidTemplate[13] = '-';
        UuidTemplate[18] = '-';
        UuidTemplate[23] = '-';
        return UuidTemplate;
    }

    public static int[] createIndices(char[] UuidTemplate) {
        var Indices = new ArrayList<Integer>();
        for (int i = 0; i < UuidTemplate.length; i++) {
            if (UuidTemplate[i] != '-') {
                Indices.add(i);
            }
        }
        return Indices.stream().mapToInt(i -> i).toArray();
    }

    public static int[] initBASE64_VALUES(String BASE64_KEYS) {
        var values = new int[123]; // max char code in base64Keys
        for (var i = 0; i < 123; ++i) {  // fill with placeholder('=') index
            values[i] = 64;
        }
        for (var i = 0; i < 64; ++i) {
            values[BASE64_KEYS.charAt(i)] = i;
        }
        return values;
    }

    public static int[] initHexMap(char[] HexChars) {
        var HexMap = new int['f' + 1];
        for (int i = 0; i < HexChars.length; i++) {
            HexMap[HexChars[i]] = i;
        }
        return HexMap;
    }

}
