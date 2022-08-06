package webserver;

import webserver.handler.StaticFileHandler;
import webserver.http.HttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HandlerMapping {

    private final Map<RequestMappingInfo, Handler> handlerRegistry;

    private final Handler fallbackHandler;

    HandlerMapping(RequestMappingRegistration... registrations) {
        this(Arrays.asList(registrations), new StaticFileHandler());
    }

    HandlerMapping(List<RequestMappingRegistration> registrations, Handler fallbackHandler) {
        this.handlerRegistry = createHandlerRegistry(registrations);
        this.fallbackHandler = fallbackHandler;
    }

    private Map<RequestMappingInfo, Handler> createHandlerRegistry(List<RequestMappingRegistration> registrations) {
        Map<RequestMappingInfo, Handler> handlerRegistry = new HashMap<>();

        for (RequestMappingRegistration registration : registrations) {
            checkDuplicatedMapping(handlerRegistry, registration);

            handlerRegistry.put(registration.getRequestMappingInfo(), registration.getHandler());
        }

        return handlerRegistry;
    }

    private void checkDuplicatedMapping(Map<RequestMappingInfo, Handler> handlerRegistry, RequestMappingRegistration registration) {
        if (handlerRegistry.containsKey(registration.getRequestMappingInfo())) {
            throw new IllegalArgumentException("이미 등록된 매핑정보 입니다. " + registration.getRequestMappingInfo());
        }
    }

    Handler getHandler(HttpRequest httpRequest) {
        return handlerRegistry.getOrDefault(
                new RequestMappingInfo(httpRequest.getPath(), httpRequest.getMethod()), fallbackHandler
        );
    }
}
