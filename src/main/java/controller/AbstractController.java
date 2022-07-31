package controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.exception.MethodNotAllowedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        HttpMethod httpMethod = request.getMethod();
        Class<?> cls = Class.forName(this.getClass().getName());
        Method[] declaredMethods = cls.getDeclaredMethods();
        Method targetMethod = Arrays.stream(declaredMethods)
                .filter(method -> isSameMethod(method.getName(), httpMethod.name()))
                .findFirst()
                .orElseThrow(() -> new MethodNotAllowedException("지원하지 않는 HTTP METHOD입니다."));

        targetMethod.invoke(this, request, response);
    }

    private boolean isSameMethod(String classMethodName, String httpMethodName) {
        String transHttpMethodName = "do" + httpMethodName;

        return transHttpMethodName.toLowerCase().equals(classMethodName.toLowerCase());
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
    }

    protected void doPut(HttpRequest request, HttpResponse response) throws Exception {
    }

    protected void doPatch(HttpRequest request, HttpResponse response) throws Exception {
    }

    protected void doDelete(HttpRequest request, HttpResponse response) throws Exception {
    }
}
