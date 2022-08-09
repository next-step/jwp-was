package webserver.http.model;

import webserver.controller.UserController;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum HandlerAdapter {
    CREATE_USER_GET(Method.GET, "/user/create", UserController.of(), "createUserGet", false),
    CREATE_USER_POST(Method.POST, "/user/create", UserController.of(), "createUserPost", false),
    LOGIN(Method.POST, "/user/login", UserController.of(), "login", false),
    USER_LIST(Method.GET, "/user/list", UserController.of(), "retrieveUsers", true),
    INDEX(Method.GET, "/index.html", UserController.of(), "index", false);

    private Method httpMethod;
    private String path;
    private Object classObject;
    private String methodName;
    private boolean accessibleAfterLogin;

    HandlerAdapter(Method httpMethod, String path, Object classObject, String methodName, boolean accessibleAfterLogin) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.classObject = classObject;
        this.methodName = methodName;
        this.accessibleAfterLogin = accessibleAfterLogin;
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

    public static boolean accessiblePagesAfterLogin(HttpRequest httpRequest) {
        return Arrays.stream(values()).filter(handlerAdapterEnum -> handlerAdapterEnum.httpMethod == httpRequest.getRequestLine().getMethod())
                .filter(handlerAdapterEnum -> handlerAdapterEnum.path.equals(httpRequest.path()))
                .map(handlerAdapterEnum -> handlerAdapterEnum.accessibleAfterLogin).findFirst().orElse(true);
    }
}
