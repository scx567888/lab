package cool.scx.cocos_decoder.test;

import org.testng.Assert;

import static cool.scx.cocos_decoder.CocosUUIDUtils.compressUUID;
import static cool.scx.cocos_decoder.CocosUUIDUtils.decompressUUID;

public class CocosUUIDUtilsTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    public static void test1() {
        var encoded = "fcmR3XADNLgJ1ByKhqcC5Z";
        var decoded = "fc991dd7-0033-4b80-9d41-c8a86a702e59";
        var uuid = decompressUUID(encoded);
        var cuuid = compressUUID(uuid);
        Assert.assertEquals(uuid, decoded);
        Assert.assertEquals(cuuid, encoded);
    }

    public static void test2() {
        var encoded = "2fkGWtA3tNPY4mxAw5aggP@6c48a";
        var decoded = "2f9065ad-037b-4d3d-8e26-c40c396a080f@6c48a";
        var uuid = decompressUUID(encoded);
        var cuuid = compressUUID(uuid);
        Assert.assertEquals(uuid, decoded);
        Assert.assertEquals(cuuid, encoded);
    }

    public static void test3() {
        var encoded = "f9PAVgqAJCBI9mVlX974oI@f9941";
        var decoded = "f93c0560-a802-4204-8f66-5655fdef8a08@f9941";
        var uuid = decompressUUID(encoded);
        var cuuid = compressUUID(uuid);
        Assert.assertEquals(uuid, decoded);
        Assert.assertEquals(cuuid, encoded);
    }


}
