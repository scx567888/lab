package cool.scx.lab.jvm;

import com.fasterxml.jackson.core.JsonProcessingException;
import cool.scx.common.util.ObjectUtils;

public class Main1 {

    public static void main(String[] args) throws JsonProcessingException {
        var a = new A();
        var b = new B();
        a.b = b;
        a.name = "123";
        b.a = a;
        b.age = 18;

        String s = ObjectUtils.toJson(a);
        System.out.println(s);
    }

    public static class A {
        public B b;
        public String name;
    }

    public static class B {
        public int age;
        public A a;
    }

}
