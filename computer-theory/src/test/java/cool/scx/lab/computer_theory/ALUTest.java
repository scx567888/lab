package cool.scx.lab.computer_theory;

import org.testng.Assert;
import org.testng.annotations.Test;

import static cool.scx.lab.computer_theory.ALU.*;
import static cool.scx.lab.computer_theory.Utils.binaryToDecimal;
import static cool.scx.lab.computer_theory.Utils.decimalToBinary;
import static org.testng.AssertJUnit.assertEquals;

/**
 * 模拟 算术逻辑单元
 */
public class ALUTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {

        //测试半加法器 (halfAdder)
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(i + j, binaryToDecimal(halfAdder(i, j)));
            }
        }

        // 测试全加法器 (fullAdder)
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    assertEquals(i + j + k, binaryToDecimal(fullAdder(i, j, k)));
                }
            }
        }

        // 8 位行波进位加法器 (8BitRippleCarryAdder)
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                var a = decimalToBinary(i);
                var b = decimalToBinary(j);
                try {
                    var r = _8BitRippleCarryAdder(a, b);
                    assertEquals(i + j, binaryToDecimal(r));
                } catch (Exception e) {
                    // 数据溢出
                }
            }
        }

        // 判断是否为 0 (isZero)
        for (int i = 0; i < 256; i++) {
            var a = decimalToBinary(i);
            var r = isZero(a);
            if ((i == 0) != r) {
                Assert.fail("错误 !!!");
            }
        }

    }

}
