package cool.scx.lab.ffm.ffm.win32.type;

import cool.scx.lab.ffm.ffm.type.Callback;

import java.lang.foreign.MemorySegment;

public interface WNDENUMPROC extends Callback {

    boolean callback(MemorySegment hwnd, long lParam);

}
