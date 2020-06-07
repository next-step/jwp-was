package web.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class InvocableHandlerMethod {

    private final static Logger logger = LoggerFactory.getLogger(InvocableHandlerMethod.class);

    private Object instance;
    private Method method;

    private MethodParameter[] parameters;

    private InvocableHandlerMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;

        this.parameters = initMethodParameters();
    }

    private MethodParameter[] initMethodParameters() {
        int count = this.method.getParameterCount();

        MethodParameter[] result = new MethodParameter[count];

        for(int i = 0; i < count; i++) {
            MethodParameter methodParameter = MethodParameter.of(this.method, i);
            result[i] = methodParameter;
        }
        return result;
    }

    public static InvocableHandlerMethod of(Object instance, Method method) {
        return new InvocableHandlerMethod(instance, method);
    }

    public Object invoke(Object... providedArguments) {
        Object[] args = getMethodArgumentValues(providedArguments);
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch(InvocationTargetException e) {
            logger.error(e.getTargetException().getMessage(), e.getTargetException());
            throw new RuntimeException(e);
        }
    }

    private Object[] getMethodArgumentValues(Object[] providedArguments) {
        Object[] args = new Object[parameters.length];

        for(int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];

            args[i] = findProvidedArgument(parameter, providedArguments);

            if(args[i] != null) {
                continue;
            }
        }

        return args;
    }

    private static Object findProvidedArgument(MethodParameter parameter, Object ...providedArgs) {
        if(providedArgs == null) {
            return null;
        }

        for (Object providedArg : providedArgs) {
            if(parameter.getParameterType().isInstance(providedArg)) {
                return providedArg;
            }
        }

        return null;
    }

    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public MethodParameter[] getParameters() {
        return parameters;
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
