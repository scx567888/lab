package cool.scx.lab.future;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PromiseImpl<T> implements Promise<T> {

    private static final Object NULL_VALUE = new Object();

    /**
     * 同时表示当前 Future 的状态
     * null 为未完成
     * NULL_VALUE 为完成但是结果为 Null
     * ThrowableValue 为失败
     * 其余值 为成功
     */
    private Object value;
    private final List<Consumer<T>> successListeners;
    private final List<Consumer<Throwable>> failListeners;

    public PromiseImpl() {
        this.value = null;
        this.successListeners = new ArrayList<>();
        this.failListeners = new ArrayList<>();
    }

    @Override
    public T await() {
        return null;
    }

    @Override
    public Future<T> onSuccess(Consumer<T> onSuccess) {
        if (this.value != null && !(this.value instanceof ThrowableValue)) {
            var i = (T) this.value;
            onSuccess.accept(i);
        }
        this.successListeners.add(onSuccess);
        return this;
    }

    @Override
    public Future<T> onFail(Consumer<Throwable> onFail) {
        if (this.value != null && this.value instanceof ThrowableValue i) {
            onFail.accept(i.cause());
        }
        this.failListeners.add(onFail);
        return this;
    }

    @Override
    public Future<T> removeSuccess(Consumer<T> onSuccess) {
        this.successListeners.remove(onSuccess);
        return this;
    }

    @Override
    public Future<T> removeFail(Consumer<Throwable> onFail) {
        this.failListeners.remove(onFail);
        return this;
    }

    @Override
    public void success(T result) {
        if (value != null) {
            throw new RuntimeException("Value already set");
        }
        this.value = result == null ? NULL_VALUE : result;
        emitSuccess();
    }

    @Override
    public void fail(Throwable cause) {
        if (value != null) {
            throw new RuntimeException("Value already set");
        }
        this.value = new ThrowableValue(cause);
        emitFail();
    }

    @SuppressWarnings("unchecked")
    private void emitSuccess() {
        var i = (T) this.value;
        for (var onSuccess : successListeners) {
            onSuccess.accept(i);
        }
    }

    private void emitFail() {
        var i = (ThrowableValue) this.value;
        for (var onFail : failListeners) {
            onFail.accept(i.cause());
        }
    }

}
