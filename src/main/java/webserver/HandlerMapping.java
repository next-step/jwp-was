package webserver;

import webserver.http.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HandlerMapping {

    private final Map<RequestMappingInfo, Handler> handlerRegistry;

    HandlerMapping(Map<RequestMappingInfo, Handler> handlers) {
        this.handlerRegistry = handlers;
    }

    HandlerMapping(List<RequestMappingRegistration> registrations) {
        this.handlerRegistry = createHandlerRegistry(registrations);
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

    Handler getHandler(Request request) {
        Handler handler = handlerRegistry.get(new RequestMappingInfo(request.getPath(), request.getMethod()));

        if (handler != null) {
            return handler;
        }

        return handlerRegistry.keySet()
                .stream()
                .filter(requestMappingInfo -> requestMappingInfo.matchRequest(request))
                .map(handlerRegistry::get)
                .findAny()
                .orElseThrow();
    }
}
