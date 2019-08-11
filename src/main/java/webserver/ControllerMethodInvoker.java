package webserver;

import annotation.RequestBody;
import model.http.Query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ControllerMethodInvoker {

    public static Object invoke(Method method, Query query) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> parameterObjects = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                Field[] fields = parameter.getType().getDeclaredFields();
                Class clazz = parameter.getType();
                Object instance = clazz.newInstance();
                for (Field field : fields) {
                    query.findByName(field.getName().toLowerCase())
                            .ifPresent(queryParameter -> {
                                field.setAccessible(true);
                                try {
                                    field.set(instance, queryParameter.getValue());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            });
                }
                parameterObjects.add(instance);
            }
        }
        return method.invoke(method.getDeclaringClass().newInstance(), parameterObjects.toArray());
    }
}
