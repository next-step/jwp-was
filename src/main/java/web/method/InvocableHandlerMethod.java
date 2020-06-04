package web.method;

import java.lang.reflect.Method;
import java.util.Objects;

public class InvocableHandlerMethod {

    private Object instance;
    private Method method;

    public InvocableHandlerMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public static InvocableHandlerMethod from(Object instance, Method method) {
        return new InvocableHandlerMethod(instance, method);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvocableHandlerMethod that = (InvocableHandlerMethod) o;
        return Objects.equals(instance, that.instance) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance, method);
    }
}
