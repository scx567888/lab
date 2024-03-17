package cool.scx.lab.reflect_and_invoke;

import cool.scx.lab.reflect_and_invoke.bean.Person;
import cool.scx.lab.reflect_and_invoke.bean.User;
import cool.scx.common.util.ObjectUtils;

import java.lang.invoke.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ObjectHelper<T> {

    public final Class<T> clazz;
    public final MethodHandles.Lookup lookup;
    public final Constructor<T> constructor;
    public final Map<String,Supplier<T>>  constructorSupplier;
    public final Map<Field, BiConsumer<T, ?>> fieldSetterMap;

    public ObjectHelper(Class<T> clazz) {
        this.clazz = clazz;
        this.lookup = MethodHandles.lookup();
        this.constructor = initConstructor(clazz);
        this.constructorSupplier =Map.of("123", initConstructorSupplier(lookup, constructor));
        this.fieldSetterMap = initFieldSetterMap(lookup, clazz.getFields());
    }

    private static <T> Map<Field, BiConsumer<T, ?>> initFieldSetterMap(MethodHandles.Lookup lookup, Field[] fields) {
        var map = new HashMap<Field, BiConsumer<T, ?>>();
        for (Field field : fields) {
            map.put(field, createSetter(lookup, field));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static <T> BiConsumer<T, ?> initFieldSetter(MethodHandles.Lookup lookup, Field field) {
        try {
            final MethodHandle setter = lookup.unreflectSetter(field);
            var site = LambdaMetafactory.metafactory(lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    setter,
                    setter.type());
            return (BiConsumer<T, ?>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <C, V> BiConsumer<C, V> createSetter(
            MethodHandles.Lookup lookup, Field field) {
        try {
            final MethodHandle setter = lookup.unreflectSetter(field);
            MethodType type = setter.type();
            if (field.getType().isPrimitive()) {
                type = type.wrap().changeReturnType(void.class);
            }
            final CallSite site = LambdaMetafactory.metafactory(lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class, MethodHandle.class),
                    type.erase(),
                    MethodHandles.exactInvoker(setter.type()),
                    type);
            return (BiConsumer<C, V>) site.getTarget().invokeExact(setter);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> Supplier<T> initConstructorSupplier(MethodHandles.Lookup lookup, Constructor<T> constructor) {
        try {
            var c = lookup.unreflectConstructor(constructor);
            var site = LambdaMetafactory.metafactory(lookup,
                    "get",
                    MethodType.methodType(Supplier.class),
                    MethodType.methodType(Object.class),
                    c,
                    c.type());
            return (Supplier<T>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Constructor<T> initConstructor(Class<T> tClass) {
        try {
            var s= ObjectUtils.convertValue(Map.of("name","司昌旭"), Person.class);
            Constructor<?>[] constructors = tClass.getConstructors();
            var c = tClass.getConstructor();
            c.setAccessible(true);
            return c;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public T newInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException {
            return constructor.newInstance();
    }

    public T newInstance1() {
        return constructorSupplier.get("123").get();
    }

    public static void main1(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var o = new ObjectHelper<>(Person.class);
        var p = o.newInstance();
        var p1 = o.newInstance1();
        System.out.println(p);
    }

    public static ObjectHelper o;

    public static void main(String[] args) throws Throwable {
        o = new ObjectHelper<>(User.class);
        System.out.println("---- normal ----");
        normal();
        System.out.println("---- methodHandle ----");
        methodHandle();
        System.out.println("---- methodHandle1 ----");
        methodHandle1();
    }

    public static Object normal() throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = new User();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

    public static Object methodHandle() throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = o.newInstance();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

    public static Object methodHandle1() throws Throwable {
        Object value = null;
        for (int outer = 0; outer < 50; outer++) {
            long start = System.nanoTime();
            for (int i = 0; i < 100000000; i++) {
                value = o.newInstance1();
            }
            long time = (System.nanoTime() - start) / 1000000;
            System.out.println("it took" + time + "ms");
        }
        return value;
    }

}
