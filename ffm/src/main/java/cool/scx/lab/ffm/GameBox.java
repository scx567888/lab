package cool.scx.lab.ffm;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import cool.scx.common.ansi.Ansi;
import cool.scx.lab.ffm.ffm.type.IntRef;
import cool.scx.lab.ffm.ffm.win32.type.RECT;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static cool.scx.lab.ffm.Helper.FindAllWindow;
import static cool.scx.lab.ffm.ffm.win32.Kernel32.KERNEL32;
import static cool.scx.lab.ffm.ffm.win32.User32.USER32;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public class GameBox {


    public static void main3(String[] args) {
        var s = new int[Integer.MAX_VALUE / 5];
        MemorySegment memorySegment = MemorySegment.ofArray(s);
        System.out.println();
//        memorySegment.set(JAVA_LONG,0,1);
    }

    public static void main(String[] args) throws Throwable {
        //获取记事本
        var notepad = USER32.FindWindowA("Notepad", null);
        //最小化
//        boolean b1 = USER32.CloseWindow(notepad);

        var sd = new RECT();
        boolean b3 = USER32.GetWindowRect(notepad, sd);

        var d = new WinDef.RECT();
        boolean bb = User32.INSTANCE.GetWindowRect(new WinDef.HWND(new Pointer(notepad.address())), d);
//        boolean b2 = USER32.BringWindowToTop(notepad);


        System.out.println();

        var windowInfos = FindAllWindow();

        var s = windowInfos.stream().filter(c -> c.isVisible() && !c.title().isEmpty()).toList();

        for (WindowInfo windowInfo : s) {
            if (windowInfo.className().equals("Notepad")) {
                var p = Arena.global().allocateFrom(JAVA_LONG, 0x57);
                var p1 = Arena.global().allocateFrom(JAVA_LONG, 0x57);
                MemorySegment memorySegment = MemorySegment.ofArray(new int[]{0x57});
                boolean b = USER32.SendMessageW(windowInfo.hWnd(), 0x0100, p, p1);
                System.out.println(b);
            }
        }

        System.out.println();
    }

    public static void main1(String[] args) {

        // See https://learn.microsoft.com/zh-cn/windows/console/getstdhandle
        var hOut = KERNEL32.GetStdHandle(-11);

        // See https://learn.microsoft.com/zh-cn/windows/console/getconsolemode
        var lpModeMemorySegment = new IntRef(0);
        KERNEL32.GetConsoleMode(hOut, lpModeMemorySegment);

        // See https://learn.microsoft.com/zh-cn/windows/console/setconsolemode
        int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;

        long lpMode = lpModeMemorySegment.getValue();
        long dwMode = lpMode | ENABLE_VIRTUAL_TERMINAL_PROCESSING;
        KERNEL32.SetConsoleMode(hOut, dwMode);

        Ansi.ansi().red(123).print();


    }


}
