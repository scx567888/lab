package cool.scx.lab.json_parser;

import java.util.*;

/**
 * 简易 JSON 解析器练习
 * todo 解析出错时提示友好的错误信息
 */
public final class JsonParser {

    /**
     * 数据
     */
    private final char[] data;

    /**
     * 指针
     */
    private int pointer;

    /**
     * 初始化 指针和字符串数据
     *
     * @param json json 字符串
     */
    private JsonParser(String json) {
        this.pointer = 0;
        this.data = json.toCharArray();
    }

    /**
     * 解析 json 字符串并返回解析后的数据
     *
     * @param json json
     * @return 解析后的数据
     */
    public static Object read(String json) {
        return new JsonParser(json).parseValue();
    }

    /**
     * 将对象转换为 json 字符串
     *
     * @param o 对象
     * @return a
     */
    public static String toJson(Object o) {
        return toJson(o, false);
    }

    /**
     * 将对象转换为 json 字符串
     *
     * @param o        对象
     * @param beautify 是否格式化
     * @return json
     */
    public static String toJson(Object o, boolean beautify) {
        return toJson0(o, 0, beautify);
    }

    /**
     * 将对象转换为 json 字符串
     *
     * @param o        对象
     * @param deep     当前层级
     * @param beautify 是否格式化
     * @return json
     */
    private static String toJson0(Object o, int deep, boolean beautify) {
        var openBrace = "{";
        var closeBrace = "}";
        var leftBracket = "[";
        var rightBracket = "]";
        var comma = ",";
        var colon = ":";
        var tab = "";
        if (beautify) {
            openBrace = "{" + System.lineSeparator();
            closeBrace = System.lineSeparator() + " ".repeat(deep * 2) + "}";
            leftBracket = "[" + System.lineSeparator();
            rightBracket = System.lineSeparator() + " ".repeat(deep * 2) + "]";
            comma = "," + System.lineSeparator();
            colon = ": ";
            tab = " ".repeat((deep + 1) * 2);
        }
        if (o instanceof Map<?, ?> m) {
            var i = m.entrySet().iterator();
            var sb = new StringBuilder(openBrace);
            while (true) {
                var e = i.next();
                var key = e.getKey();
                var value = e.getValue();
                sb.append(tab).append('"').append(key).append('"');
                sb.append(colon);
                sb.append(toJson0(value, deep + 1, beautify));
                if (!i.hasNext()) {
                    return sb.append(closeBrace).toString();
                }
                sb.append(comma);
            }
        } else if (o instanceof String s) {
            return '"' + s + '"';
        } else if (o instanceof List<?> l) {
            var i = l.iterator();
            var sb = new StringBuilder(leftBracket);
            while (true) {
                var value = i.next();
                sb.append(tab).append(toJson0(value, deep + 1, beautify));
                if (!i.hasNext()) {
                    return sb.append(rightBracket).toString();
                }
                sb.append(comma);
            }
        } else {
            return o.toString();
        }
    }

    /**
     * 判断字符是否属于 可以构成 json 数字值的部分
     *
     * @param c 字符
     * @return 结果
     */
    private static boolean isNumberChar(char c) {
        return switch (c) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.' -> true;
            default -> false;
        };
    }

    /**
     * 解析值
     *
     * @return 对象
     */
    private Object parseValue() {
        skipBlank();// 先跳过最开始的空白字符
        return switch (data[pointer]) {
            case '{' -> parseObject();
            case '[' -> parseArray();
            case 'n' -> parseNull();
            case 't' -> parseTrue();
            case 'f' -> parseFalse();
            case '"' -> parseString();
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-' -> parseNumber();
            default -> null;
        };
    }

    /**
     * 解析对象
     *
     * @return 返回 map
     */
    private Map<String, Object> parseObject() {
        pointer = pointer + 1;// 跳过 "{"
        skipBlank();// 清除无用字符
        var result = new LinkedHashMap<String, Object>();
        while (data[pointer] != '}') {
            var key = parseString();
            skipColon();// 跳过冒号
            var value = parseValue();
            result.put(key, value);
            skipComma();// 跳过逗号 为下一次循环做准备
        }
        pointer = pointer + 1;// 跳过 "}"
        return result;
    }

    /**
     * 解析数组
     *
     * @return 返回 List
     */
    private List<Object> parseArray() {
        pointer = pointer + 1;// 跳过 "["
        skipBlank();// 清除无用字符
        var result = new ArrayList<>();
        while (data[pointer] != ']') {
            result.add(parseValue());
            skipComma();// 跳过逗号 为下一次循环做准备
        }
        pointer = pointer + 1;// 跳过 "]"
        return result;
    }

    /**
     * 解析 null
     *
     * @return null
     */
    private Object parseNull() {
        var content = new String(Arrays.copyOfRange(data, pointer, pointer + 4));
        if ("null".equals(content)) {
            pointer = pointer + 4;// 跳过 null
            return null;
        } else {
            throw new RuntimeException("解析失败 : " + pointer);
        }
    }

    /**
     * 解析 true
     *
     * @return true
     */
    private boolean parseTrue() {
        var content = new String(Arrays.copyOfRange(data, pointer, pointer + 4));
        if ("true".equals(content)) {
            pointer = pointer + 4;// 跳过 true
            return true;
        } else {
            throw new RuntimeException("解析失败 : " + pointer);
        }
    }

    /**
     * 解析 false
     *
     * @return false
     */
    private boolean parseFalse() {
        var content = new String(Arrays.copyOfRange(data, pointer, pointer + 5));
        if ("false".equals(content)) {
            pointer = pointer + 5;// 跳过 false
            return false;
        } else {
            throw new RuntimeException("解析失败 : " + pointer);
        }
    }

    /**
     * 解析字符串
     *
     * @return 字符串
     */
    private String parseString() {
        pointer = pointer + 1;// 跳过 " (开头的双引号)
        skipBlank();// 清除无用字符
        var result = new StringBuilder();
        while (data[pointer] != '"') {
            result.append(data[pointer]);
            pointer = pointer + 1;
        }
        pointer = pointer + 1;// 跳过 " (结尾的双引号)
        return result.toString();
    }

    /**
     * 解析 数组
     *
     * @return int 或 double
     */
    private Object parseNumber() {
        var numStr = new StringBuilder();
        var hasDecimalPoint = false;// 是否有小数点
        while (isNumberChar(data[pointer])) {
            numStr.append(data[pointer]);
            if (data[pointer] == '.') {// 有小数点
                hasDecimalPoint = true;
            }
            pointer = pointer + 1;
        }
        if (hasDecimalPoint) {// 根据是否有小数点来返回 int 或者 double
            return Double.parseDouble(numStr.toString());
        } else {
            return Integer.parseInt(numStr.toString());
        }
    }

    /**
     * 跳过空白的字符串(不影响解析的字符串) 比如空格换行之类
     */
    private void skipBlank() {
        while (true) {
            var c = data[pointer];
            var needSkip = c == ' ' || c == '\t' || c == '\n' || c == '\r';
            if (needSkip) {
                pointer = pointer + 1;
            } else {
                return;
            }
        }
    }

    /**
     * 跳过 ":" (冒号), 同时也包括 skipBlank() 中的所有情况
     */
    private void skipColon() {
        while (true) {
            var c = data[pointer];
            var needSkip = c == ':' || c == ' ' || c == '\t' || c == '\n' || c == '\r';
            if (needSkip) {
                pointer = pointer + 1;
            } else {
                return;
            }
        }
    }

    /**
     * 跳过 "," (逗号), 同时也包括 skipBlank() 中的所有情况
     */
    private void skipComma() {
        while (true) {
            var c = data[pointer];
            var needSkip = c == ',' || c == ' ' || c == '\t' || c == '\n' || c == '\r';
            if (needSkip) {
                pointer = pointer + 1;
            } else {
                return;
            }
        }
    }

}
