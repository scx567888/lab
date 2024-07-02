package cool.scx.lab.ffm;

import java.lang.foreign.MemorySegment;

public record WindowInfo(MemorySegment hWnd,
                         String className,
                         String title,
                         boolean isVisible,
                         MemorySegment threadProcessId) {

}
