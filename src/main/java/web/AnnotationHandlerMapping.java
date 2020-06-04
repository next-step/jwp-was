package web;

import annotations.Controller;
import annotations.RequestMapping;
import http.HttpRequest;
import web.method.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {

    private Map<Class<?>, Object> controllers;

    private Map<HandlerKey, InvocableHandlerMethod> handlers = new LinkedHashMap<>();

    public AnnotationHandlerMapping(Map<Class<?>, Object> controllers) {
        this.controllers = controllers;

        Set<Method> requestMappingMethods = getRequestMappingMethods(controllers.keySet());
        initHandlers(requestMappingMethods);
    }

    private void initHandlers(Set<Method> requestMappingMethods) {
        requestMappingMethods.forEach(method -> {
            HandlerKey handlerKey = HandlerKey.from(method);
            InvocableHandlerMethod invocableHandlerMethod = InvocableHandlerMethod.from(this.controllers.get(method.getDeclaringClass()), method);

            handlers.put(handlerKey, invocableHandlerMethod);
        });
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        Set<Method> requestMappingMethods = new LinkedHashSet<>();
        for (Class<?> controller : controllers) {
               requestMappingMethods.addAll(
                       Arrays.stream(controller.getDeclaredMethods())
                               .filter(method -> method.getDeclaredAnnotation(RequestMapping.class) != null)
                               .collect(Collectors.toSet())
               );
        }
        return requestMappingMethods;
    }

    public InvocableHandlerMethod getHandler(HttpRequest httpRequest) {
        HandlerKey handlerKey = HandlerKey.of(httpRequest.getPath(), httpRequest.getMethod());

        Optional<HandlerKey> handlerKeyOptional  = handlers.keySet().stream()
                .filter(handler -> handlerKey.equals(handler))
                .findAny();

        if(!handlerKeyOptional.isPresent()) {
            return null;
        }

        return handlers.get(handlerKeyOptional.get());
    }
}
