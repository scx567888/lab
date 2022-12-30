package cool.scx.test.foreign_function;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static cool.scx.learn.foreign_function.Demo1.detectIfAnsiCapable;

public class Test1 {

    public static void main(String[] args) {
        beforeTest();
        test1();
    }

    @BeforeTest
    public static void beforeTest() {
        var b = detectIfAnsiCapable();
        System.out.println("支持 ansi : " + b);
    }

    @Test
    public static void test1() {
        for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                var s = new Object[3];
                s[0] = new Ansi8BitColor(i);
                s[1] = new Ansi8BitBackground(j);
                s[2] = "☻";
                System.out.print(buildEnabled(s));
            }
            System.out.println();
        }
    }

    public interface AnsiElement {

        /**
         * <p>code.</p>
         *
         * @return the ANSI escape code
         */
        String ansiCode();

    }


    private static String buildEnabled(Object[] elements) {
        StringBuilder sb = new StringBuilder();
        boolean writingAnsi = false;
        boolean containsEncoding = false;
        for (var element : elements) {
            if (element instanceof AnsiElement) {
                containsEncoding = true;
                if (!writingAnsi) {
                    sb.append(ENCODE_START);
                    writingAnsi = true;
                } else {
                    sb.append(ENCODE_JOIN);
                }
                sb.append(((AnsiElement) element).ansiCode());
            } else {
                if (writingAnsi) {
                    sb.append(ENCODE_END);
                    writingAnsi = false;
                }
                sb.append(element);
            }
        }
        if (containsEncoding) {
            sb.append(writingAnsi ? ENCODE_JOIN : ENCODE_START);
            sb.append(RESET);
            sb.append(ENCODE_END);
        }
        return sb.toString();
    }

    /**
     * 合并字符
     */
    private static final String ENCODE_JOIN = ";";

    /**
     * 起始字符
     */
    private static final String ENCODE_START = "\033[";

    /**
     * 结束字符
     */
    private static final String ENCODE_END = "m";

    /**
     * 重置
     */
    private static final String RESET = "0;" + 39;

    public record Ansi8BitColor(int code) implements AnsiElement {

        public Ansi8BitColor {
            if (code < 0 || code > 255) {
                throw new IllegalArgumentException("Code must be between 0 and 255");
            }
        }

        @Override
        public String ansiCode() {
            return "38;5;" + this.code;
        }

    }

    public record Ansi8BitBackground(int code) implements AnsiElement {

        public Ansi8BitBackground {
            if (code < 0 || code > 255) {
                throw new IllegalArgumentException("Code must be between 0 and 255");
            }
        }

        @Override
        public String ansiCode() {
            return "48;5;" + this.code;
        }

    }

}
