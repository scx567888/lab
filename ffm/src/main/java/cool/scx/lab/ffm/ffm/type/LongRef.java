package cool.scx.lab.ffm.ffm.type;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.ValueLayout.JAVA_LONG;

public class LongRef implements Ref {

    private long value;
    private MemorySegment memorySegment;

    public LongRef() {
        this(0);
    }

    public LongRef(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public MemorySegment init(Arena arena) {
        return this.memorySegment = arena.allocateFrom(JAVA_LONG, this.value);
    }

    @Override
    public void refresh() {
        this.value = this.memorySegment.get(JAVA_LONG, 0);
    }

}
