package webserver.http.model;

import webserver.controller.UserController;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum HandlerAdapter {
    CREATE_USER_GET(Method.GET, "/user/create", UserController.of(), "createUserGet"),
    CREATE_USER_POST(Method.POST, "/user/create", UserController.of(), "createUserPost"),
    LOGIN(Method.POST, "/user/login", new UserController(), "login"),
    INDEX(Method.GET, "/index.html", UserController.of(), "index");

    private final Method httpMethod;
    private final String path;
    private final Object classObject;
    private final String methodName;

    HandlerAdapter(Method httpMethod, String path, Object classObject, String methodName) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.classObject = classObject;
        this.methodName = methodName;
    }

    public static Object handlerMapping(HttpRequest httpRequest) {
        HandlerAdapter handlerAdapter = Arrays.stream(values()).filter(handlerAdapterEnum -> handlerAdapterEnum.httpMethod == httpRequest.getRequestLine().getMethod())
                .filter(handlerAdapterEnum -> handlerAdapterEnum.path.equals(httpRequest.path()))
                .findFirst().orElse(HandlerAdapter.INDEX);

        Object instance = handlerAdapter.classObject;
        Class<?> instanceClass = instance.getClass();
        try {
            java.lang.reflect.Method declaredMethod = instanceClass.getDeclaredMethod(handlerAdapter.methodName, HttpRequest.class);
            return declaredMethod.invoke(instance, httpRequest);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
