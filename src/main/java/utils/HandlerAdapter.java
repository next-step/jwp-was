package utils;

import annotation.GetMapping;
import annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import configuration.HandlerConfiguration;
import exception.HttpNotFoundException;
import model.*;
import org.springframework.util.StringUtils;
import types.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class HandlerAdapter {

    private static final HandlerAdapter instance = new HandlerAdapter();

    private Map<Object, Handlers> handlers;

    private HandlerAdapter() {
    }

    public HttpResponseMessage invoke(HttpRequestMessage httpRequestMessage) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        RequestLine requestLine = httpRequestMessage.getRequestLine();

        if (this.handlers == null) {
            this.initHandlers();
        }

        HandlerPair handlerPair = find(requestLine);
        if (handlerPair == null) {
            return null;
        }

        return this.invokeHandler(handlerPair, httpRequestMessage);
    }

    private HttpResponseMessage invokeHandler(HandlerPair handlerPair, HttpRequestMessage httpRequestMessage) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        RequestLine requestLine = httpRequestMessage.getRequestLine();
        Object controller = handlerPair.getController();
        Method handler = handlerPair.getHandler();

        Parameter[] parameters = handler.getParameters();
        if (parameters.length == 0) {
            return (HttpResponseMessage) handler.invoke(controller);
        }

        if (parameters.length == 1) {
            Parameter definedParameter = parameters[0];
            Class<?> parameterClass = definedParameter.getType();
            String data = this.getParameterData(requestLine.getHttpMethod(), httpRequestMessage);
            Object parameter = this.getParaMeterObject(data, parameterClass);
            return (HttpResponseMessage) handler.invoke(controller, parameter);
        }

        // TODO 1개 초과 파라미터 설정시 어노테이션에 따라 파라미터 컨버팅 처리 구현
        return (HttpResponseMessage) handler.invoke(controller);
    }

    private Object getParaMeterObject(String data, Class<?> parameterClass) throws JsonProcessingException {
        if (data == null) {
            return null;
        }

        return ObjectMapperFactory.getObjectMapper().readValue(data, parameterClass);
    }


    private String getParameterData(HttpMethod httpMethod, HttpRequestMessage httpRequestMessage) throws JsonProcessingException {
        UrlPath urlPath = httpRequestMessage.getRequestLine().getUrlPath();
        QueryParameter queryParameter = urlPath.getQueryParameter();

        if (httpMethod == HttpMethod.GET && queryParameter != null) {
            return ObjectMapperFactory.getObjectMapper().writeValueAsString(queryParameter.getParameters());
        }

        String body = httpRequestMessage.getBody();
        if (httpMethod == HttpMethod.POST && StringUtils.hasText(body)) {
            return ObjectMapperFactory.getObjectMapper().writeValueAsString(HttpParser.convertStringToMap(httpRequestMessage.getBody()));
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
            // TODO handler not found exception 정의
            return null;
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
                .anyMatch(annotation -> {
                    boolean isEqualAnnotation = this.isEqualAnnotation(annotation, targetAnnotation);

                    String definedPath = this.getPath(annotation);
                    boolean isEqualPath = Objects.equals(definedPath, path);

                    return isEqualAnnotation && isEqualPath;
                });
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
            return String.valueOf(pathMethod.invoke(annotation));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
