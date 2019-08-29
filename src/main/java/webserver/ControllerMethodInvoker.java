package webserver;

import annotation.RequestBody;
import model.controller.ResponseEntity;
import model.http.HttpRequest;

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

    public static ResponseEntity invoke(Method method, HttpRequest httpRequest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> parameterObjects = new ArrayList<>();

        for (Parameter parameter : getParametersWithRequestBody(method)) {
            Object instance = parameter.getType().newInstance();
            Field[] fields = parameter.getType().getDeclaredFields();
            parameterObjects.add(initFieldsWithQueryParameterValue(httpRequest, instance, fields));
        }

        return (ResponseEntity) method.invoke(method.getDeclaringClass().newInstance(), parameterObjects.toArray());
    }

    private static Object initFieldsWithQueryParameterValue(HttpRequest httpRequest, Object instance, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            Optional<String> fieldValue = httpRequest.findDataValueByName(field.getName().toLowerCase());
            if (fieldValue.isPresent()) {
                field.setAccessible(true);
                field.set(instance, fieldValue.get());
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
