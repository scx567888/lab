package cool.scx.learn.json;

import cool.scx.config.ScxEnvironment;
import cool.scx.util.ObjectUtils;
import cool.scx.util.StopWatch;

import java.io.IOException;
import java.nio.file.Files;

public class JsonTest {

    public static void main(String[] args) throws IOException {
        var environment = new ScxEnvironment(JsonTest.class);
        var json = Files.readString(environment.getPathByAppRoot("AppRoot:test.json"));

        StopWatch.start("666");
        Object s = "";
        for (int i = 0; i < 999; i++) {
            s = JsonParser.read(json);
            s = ObjectUtils.jsonMapper().readTree(json);
        }

        Object o = null;

        StopWatch.start("123");
        for (int i = 0; i < 99999; i++) {
            o = JsonParser.read(json);
        }
        System.out.println(StopWatch.stopToMillis("123"));

        System.out.println(o);

        StopWatch.start("1");
        for (int i = 0; i < 99999; i++) {
            o = ObjectUtils.jsonMapper().readTree(json);
        }
        System.out.println(StopWatch.stopToMillis("1"));

        System.out.println(o);

    }

}
