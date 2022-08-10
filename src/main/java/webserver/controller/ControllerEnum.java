package webserver.controller;

import webserver.http.model.HttpRequest;
import webserver.http.model.Method;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum ControllerEnum {
    CREATE_USER_GET(Method.GET, "/user/create", UserController.of(), "createUserGet", false),
    CREATE_USER_POST(Method.POST, "/user/create", UserController.of(), "createUserPost", false),
    LOGIN(Method.POST, "/user/login", UserController.of(), "login", false),
    USER_LIST(Method.GET, "/user/list", UserController.of(), "retrieveUsers", true),
    INDEX(Method.GET, "/index.html", UserController.of(), "index", false);

    private final Method httpMethod;
    private final String path;
    private final Object classObject;
    private final String methodName;
    private final boolean accessibleAfterLogin;

    ControllerEnum(Method httpMethod, String path, Object classObject, String methodName, boolean accessibleAfterLogin) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.classObject = classObject;
        this.methodName = methodName;
        this.accessibleAfterLogin = accessibleAfterLogin;
    }

    public static Object handlerMapping(HttpRequest httpRequest) {
        ControllerEnum controllerEnum = Arrays.stream(values()).filter(controllerEnumEnum -> controllerEnumEnum.httpMethod == httpRequest.getRequestLine().getMethod())
                .filter(controllerEnumEnum -> controllerEnumEnum.path.equals(httpRequest.path()))
                .findFirst().orElse(ControllerEnum.INDEX);

        Object instance = controllerEnum.classObject;
        Class<?> instanceClass = instance.getClass();
        try {
            java.lang.reflect.Method declaredMethod = instanceClass.getDeclaredMethod(controllerEnum.methodName, HttpRequest.class);
            return declaredMethod.invoke(instance, httpRequest);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean accessiblePagesAfterLogin(HttpRequest httpRequest) {
        return Arrays.stream(values()).filter(controllerEnumEnum -> controllerEnumEnum.httpMethod == httpRequest.getRequestLine().getMethod())
                .filter(controllerEnumEnum -> controllerEnumEnum.path.equals(httpRequest.path()))
                .map(controllerEnumEnum -> controllerEnumEnum.accessibleAfterLogin).findFirst().orElse(true);
    }
}
