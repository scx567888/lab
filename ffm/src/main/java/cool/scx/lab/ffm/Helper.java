package cool.scx.lab.ffm;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sun.jna.platform.win32.Advapi32Util.registryGetValues;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static cool.scx.lab.ffm.ffm.win32.User32.USER32;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public class Helper {

    public static final String INTERNET_SETTINGS_KEY_PATH = "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings";
    public static final String PROXY_ENABLE = "ProxyEnable";
    public static final String PROXY_SERVER = "ProxyServer";
    public static final String PROXY_OVERRIDE = "ProxyOverride";
    public static final String LOCAL_HOST = "127.0.0.1";

    public static List<WindowInfo> FindAllWindow() throws Throwable {
        var list = new ArrayList<WindowInfo>();
        USER32.EnumWindows((hWnd, _) -> {
            //只要顶级窗口
            if (USER32.GetParent(hWnd).address() == 0) {
                var title = getWindowTitle(hWnd);
                var className = getWindowClassName(hWnd);
                var isVisible = USER32.IsWindowVisible(hWnd);
                var threadProcessId = USER32.GetWindowThreadProcessId(hWnd, Arena.global().allocate(JAVA_LONG));
                list.add(new WindowInfo(hWnd, className, title, isVisible, threadProcessId));
            }
            return true;
        }, 0);

        return list;
    }

    public static String getWindowTitle(MemorySegment hWnd) {
        int l = USER32.GetWindowTextLengthW(hWnd);
        var chars = new char[l];
        int l1 = USER32.GetWindowTextW(hWnd, chars, l + 1);
        return new String(chars, 0, l1);
    }

    public static String getWindowClassName(MemorySegment hWnd) {
        var chars = new char[512];
        int l1 = USER32.GetClassNameW(hWnd, chars, 512);
        return new String(chars, 0, l1);
    }

    public static void main(String[] args) throws Throwable {
        Map<String, Object> internetSettingsValues = getInternetSettingsValues();

        USER32.MessageBoxA(null, "司昌旭", "司昌旭", 0);


        List<WindowInfo> windowInfos = FindAllWindow();
        var s = windowInfos.stream().filter(c -> c.isVisible() && !c.title().isEmpty()).toList();
        s.forEach(System.out::println);
        com.sun.jna.platform.win32.User32.INSTANCE.GetWindowText(null, new char[]{}, 1);

//        com.sun.jna.platform.win32.User32.INSTANCE.EnumWindows()

//        WinUser.GetWindowThreadProcessId()
    }

    /**
     * 获取代理配置信息
     *
     * @return 代理配置信息
     */
    public static Map<String, Object> getInternetSettingsValues() {
        return registryGetValues(
                HKEY_CURRENT_USER,
                INTERNET_SETTINGS_KEY_PATH
        );
    }

}
