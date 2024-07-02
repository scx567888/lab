package cool.scx.lab.future;

public interface Promise<T> extends Future<T> {

    static <T> Promise<T> promise() {
        return new PromiseImpl<>();
    }

    void success(T result);

    void fail(Throwable cause);

}
