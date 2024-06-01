package cool.scx.lab.json_parser;

import cool.scx.config.ScxEnvironment;
import cool.scx.common.util.ObjectUtils;
import cool.scx.common.util.StopWatch;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;

public class JsonParserTest {

    private static final ScxEnvironment environment = new ScxEnvironment(JsonParserTest.class);

    public static void main(String[] args) throws IOException {
        test1();
    }

    @Test
    private static void test1() throws IOException {
        var json = Files.readString(environment.getPathByAppRoot("AppRoot:test.json"));

        //预热
        for (int i = 0; i < 999; i++) {
            var s = JsonParser.read(json);
            s = ObjectUtils.jsonMapper().readTree(json);
            StopWatch.start(i + "-");
        }

        //临时对象
        Object o = null;


        //测试自己写的 json 解析器
        StopWatch.start("a");
        for (int i = 0; i < 99999; i++) {
            o = JsonParser.read(json);
        }
        System.out.println("my json parser : " + StopWatch.stopToMillis("a"));
        System.out.println(JsonParser.toJson(o, true));
        System.out.println(JsonParser.toJson(o));


        //测试 jackson 的解析器
        StopWatch.start("b");
        for (int i = 0; i < 99999; i++) {
            o = ObjectUtils.jsonMapper().readTree(json);
        }
        System.out.println("jackson : " + StopWatch.stopToMillis("b"));
        System.out.println(o.toString());

    }

}
