package cool.scx.learn.foreign_function;

//import java.lang.foreign.*;

//import static java.lang.foreign.ValueLayout.*;

public class Demo1 {

    public static boolean detectIfAnsiCapable() {
        String currentOS = System.getProperty("os.name");
        if (currentOS.startsWith("Windows")) {
            var winVersion = System.getProperty("os.version");
            if (winVersion.startsWith("10") || winVersion.startsWith("11")) {
                try {
                    enableWindows10AnsiSupport();
                    return true;
                } catch (Exception e) {
                    // 如果开启失败 则表示不支持
                    return false;
                }
            } else {// 不是 Windows 10 以上则表示不支持
                return false;
            }
        } else {// 不是 Windows 表示支持
            return true;
        }
    }

    /**
     * Windows 10 supports Ansi codes. However, it's still experimental and not enabled by default.
     * <br>
     * This method enables the necessary Windows 10 feature.
     * <br>
     * More info: <a href="https://stackoverflow.com/a/51681675">...</a>
     * Code source: <a href="https://stackoverflow.com/a/51681675">...</a>
     * Reported issue: <a href="https://github.com/PowerShell/PowerShell/issues/11449#issuecomment-569531747">...</a>
     */
    private static void enableWindows10AnsiSupport() {
// 此处代码为 jdk19 版本, 现已失效 需更改为 jdk20
//        try (var session = MemorySession.openConfined()) {
//            var linker = Linker.nativeLinker();
//            var kernel32 = SymbolLookup.libraryLookup("kernel32", session);
//
//            // See https://learn.microsoft.com/zh-cn/windows/console/getstdhandle
//            var GetStdHandle = kernel32.lookup("GetStdHandle").orElse(null);
//            var GetStdHandleHandle = linker.downcallHandle(GetStdHandle, FunctionDescriptor.of(ADDRESS, JAVA_LONG));
//            var hOut = GetStdHandleHandle.invoke(-11);
//
//            //See https://learn.microsoft.com/zh-cn/windows/console/getconsolemode
//            var GetConsoleMode = kernel32.lookup("GetConsoleMode").orElse(null);
//            var GetConsoleModeHandle = linker.downcallHandle(GetConsoleMode, FunctionDescriptor.of(JAVA_BOOLEAN, ADDRESS, ADDRESS));
//            var allocator = SegmentAllocator.implicitAllocator();
//            var lpModeMemorySegment = allocator.allocate(JAVA_LONG);
//            GetConsoleModeHandle.invoke(hOut, lpModeMemorySegment);
//
//            //See https://learn.microsoft.com/zh-cn/windows/console/setconsolemode
//            int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
//            var SetConsoleMode = kernel32.lookup("SetConsoleMode").orElse(null);
//            var SetConsoleModeHandle = linker.downcallHandle(SetConsoleMode, FunctionDescriptor.of(JAVA_LONG, ADDRESS, JAVA_LONG));
//            long lpMode = lpModeMemorySegment.getAtIndex(JAVA_LONG, 0);
//            var dwMode = lpMode | ENABLE_VIRTUAL_TERMINAL_PROCESSING;
//            SetConsoleModeHandle.invoke(hOut, dwMode);
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }

    }

}
