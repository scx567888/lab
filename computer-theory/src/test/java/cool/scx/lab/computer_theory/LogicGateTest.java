package cool.scx.lab.computer_theory;

import cool.scx.lab.computer_theory.LogicGate;
import org.testng.annotations.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 模拟基本逻辑门及其组合关系
 */
public class LogicGateTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {
        print("not (非门)", LogicGate::not);
        print("and (与门)", LogicGate::and);
        print("or (或门)", LogicGate::or);
        print("xor (异或门)", LogicGate::xor);
        print("nand (与非门)", LogicGate::nand);
        print("nor (或非门)", LogicGate::nor);
        print("xnor (同或门)", LogicGate::xnor);
    }

    public static void print(String name, BiFunction<Boolean, Boolean, Boolean> fun) {
        System.out.println("名称 : " + name);
        System.out.println("A   B   T");
        System.out.println("1   1   " + (fun.apply(true, true) ? 1 : 0));
        System.out.println("0   1   " + (fun.apply(false, true) ? 1 : 0));
        System.out.println("1   0   " + (fun.apply(true, false) ? 1 : 0));
        System.out.println("0   0   " + (fun.apply(false, false) ? 1 : 0));
    }

    public static void print(String name, Function<Boolean, Boolean> fun) {
        System.out.println("名称 : " + name);
        System.out.println("A   T");
        System.out.println("1   " + (fun.apply(true) ? 1 : 0));
        System.out.println("0   " + (fun.apply(false) ? 1 : 0));
    }

}
