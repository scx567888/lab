package cool.scx.lab.reflect_and_invoke;

import cool.scx.lab.reflect_and_invoke.bean.Person;
import cool.scx.lab.reflect_and_invoke.bean.User;

import java.lang.invoke.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public final class MyAccessor {

    private final Supplier getterFunction;

    public MyAccessor(Constructor<?> m) throws Throwable {
        var lookup = MethodHandles.lookup();
        CallSite site = LambdaMetafactory.metafactory(lookup,
                "get",
                MethodType.methodType(Supplier.class),
                MethodType.methodType(Object.class),
                lookup.unreflectConstructor(m),
                MethodType.methodType(Person.class));
        getterFunction = (Supplier) site.getTarget().invokeExact();
    }

    public Object executeGetter() {
        return getterFunction.get();
    }

    public MyAccessor(Method method) throws Throwable {
        var lookup = MethodHandles.lookup();
        var callSite = LambdaMetafactory.metafactory(lookup,
                "get",
                MethodType.methodType(Supplier.class),
                MethodType.methodType(String.class),
                lookup.unreflect(method),
                MethodType.methodType(Object.class));
        this.getterFunction = (Supplier) callSite.getTarget().invoke();
    }

    public Object executeGetter(Object bean, Object... os) {
//        return getterFunction.h(bean);
        return null;
    }

    public static MethodType getMethodType(Method method) {
        return MethodType.methodType(method.getReturnType(), method.getParameterTypes());
    }

    public static MethodHandle getMethodHandle(MethodHandles.Lookup lookup, Method method) throws NoSuchMethodException, IllegalAccessException {
        return lookup.findVirtual(method.getDeclaringClass(), method.getName(), getMethodType(method));
    }

    public static MethodHandle getMethodHandle1(MethodHandles.Lookup lookup, Method method) throws NoSuchMethodException, IllegalAccessException {
        return lookup.unreflect(method);
    }

    public static void main(String[] args) throws Throwable {
        var m = User.class.getMethod("getName");
        var bb=new MyAccessor(m);
        var lookup = MethodHandles.lookup();
        MethodHandle methodHandle = getMethodHandle1(lookup, m);
//        lookup.unreflectConstructor(m)
//        Person person = new Person();
//        person.name = "司昌旭";
//        var s = methodHandle.invoke(person);
        System.out.println();
    }

}
