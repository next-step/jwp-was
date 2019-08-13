package webserver;

import annotation.RequestBody;
import model.http.Query;
import model.http.QueryParameter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControllerMethodInvoker {

    public static Object invoke(Method method, Query query) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> parameterObjects = new ArrayList<>();

        for (Parameter parameter : getParametersWithRequestBody(method)) {
            Object instance = parameter.getType().newInstance();
            Field[] fields = parameter.getType().getDeclaredFields();
            parameterObjects.add(initFieldsWithQueryParameterValue(query, instance, fields));
        }

        return method.invoke(method.getDeclaringClass().newInstance(), parameterObjects.toArray());
    }

    private static Object initFieldsWithQueryParameterValue(Query query, Object instance, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            Optional<QueryParameter> queryParameter = query.findByName(field.getName().toLowerCase());
            if (queryParameter.isPresent()) {
                field.setAccessible(true);
                field.set(instance, queryParameter.get().getValue());
            }
        }
        return instance;
    }

    private static List<Parameter> getParametersWithRequestBody(Method method) {
        return Arrays.stream(method.getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(RequestBody.class))
                .collect(Collectors.toList());
    }
}
