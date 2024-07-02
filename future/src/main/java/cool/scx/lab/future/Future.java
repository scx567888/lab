package cool.scx.lab.future;

import java.util.function.Consumer;

public interface Future<T> {

    static Future<?> all(Future<?>... futures) {
        return null;
    }

    static Future<?> any(Future<?>... futures) {
        return null;
    }

    T await();

    Future<T> onSuccess(Consumer<T> onSuccess);

    Future<T> onFail(Consumer<Throwable> onFail);

    Future<T> removeSuccess(Consumer<T> onSuccess);

    Future<T> removeFail(Consumer<Throwable> onFail);

}
