package utils;

import annotation.GetMapping;
import configuration.HandlerConfiguration;
import exception.HttpNotFoundException;
import model.HandlerPair;
import model.Handlers;
import model.HttpMessage;
import model.RequestLine;
import org.springframework.web.bind.annotation.PostMapping;
import types.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class HandlerAdapter {

    private static final HandlerAdapter instance = new HandlerAdapter();

    private Map<Object, Handlers> handlers;

    private HandlerAdapter() {
    }

    public Object invoke(HttpMessage httpMessage) {
        RequestLine requestLine = httpMessage.getRequestLine();
        if (this.handlers == null) {
            this.initHandlers();
        }

        HandlerPair handlerPair = find(requestLine);
        try {
            return handlerPair.getHandler().invoke(handlerPair.getController());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HandlerAdapter getInstance() {
        return instance;
    }

    private void initHandlers() {
        handlers = new HashMap<>();
        List<Object> controllers = HandlerConfiguration.getInstance().getControllers();
        controllers.forEach(controller -> {
            List<Method> methods = List.of(controller.getClass().getDeclaredMethods());
            handlers.put(controller, new Handlers(methods));
        });
    }

    private HandlerPair find(RequestLine requestLine) {

        List<HandlerPair> handlerPairs = new ArrayList<>();
        for (Map.Entry<Object, Handlers> entry : handlers.entrySet()) {
            Object controller = entry.getKey();
            Handlers handlers = entry.getValue();
            Method handler = handlers.getHandlers()
                    .stream()
                    .filter(method -> findHandler(requestLine, method))
                    .findAny()
                    .orElse(null);
            this.collectHandlerPair(handlerPairs, controller, handler);
        }

        if (handlerPairs.size() != 1) {
            throw new IllegalArgumentException(); // TODO 1 unique(HttpMethod + path) : 1 handler validation
        }

        return handlerPairs.get(0);
    }

    private void collectHandlerPair(List<HandlerPair> handlerPairs, Object controller, Method handler) {
        if (handler != null) {
            handlerPairs.add(new HandlerPair(controller, handler));
        }
    }

    private boolean findHandler(RequestLine requestLine, Method method) {
        HttpMethod httpMethod = requestLine.getHttpMethod();
        String path = requestLine.getUrlPath().getPath();

        String postFix = "Mapping";
        String httpMethodName = httpMethod.name();
        String annotationTypeName = httpMethodName.substring(0, 1).toUpperCase() + httpMethodName.toLowerCase().substring(1) + postFix;
        Class<?> targetAnnotation = this.getAnnotationType(annotationTypeName);

        return Arrays.stream(method.getAnnotations())
                .anyMatch(annotation -> this.isEqualAnnotation(annotation, targetAnnotation) && Objects.equals(this.getPath(annotation), path));
    }

    private boolean isEqualAnnotation(Annotation annotation, Class<?> targetAnnotation) {
        Class<?> clazz = annotation.annotationType();
        return clazz.getName().equals(targetAnnotation.getName());
    }

    private Class<?> getAnnotationType(String annotationTypeName) {
        if (annotationTypeName.equals("GetMapping")) {
            return GetMapping.class;
        }

        if (annotationTypeName.equals("PostMapping")) {
            return PostMapping.class;
        }

        throw new HttpNotFoundException();
    }

    private String getPath(Annotation annotation) {
        Class<?> clazz = annotation.annotationType();
        Method pathMethod = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().equals("path"))
                .findAny()
                .orElseThrow(HttpNotFoundException::new);

        try {
            return (String) pathMethod.invoke(annotation);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
