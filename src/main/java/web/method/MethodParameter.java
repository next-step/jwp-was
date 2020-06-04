package web.method;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodParameter {

    private Method method;
    private int parameterIndex;

    private Class<?> parameterType;

    public MethodParameter(Method method, int parameterIndex) {
        this.method = method;
        this.parameterIndex = parameterIndex;

        this.parameterType = method.getParameterTypes()[this.parameterIndex];
    }

    public Method getMethod() {
        return method;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodParameter that = (MethodParameter) o;
        return parameterIndex == that.parameterIndex &&
                Objects.equals(method, that.method) &&
                Objects.equals(parameterType, that.parameterType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, parameterIndex, parameterType);
    }
}
