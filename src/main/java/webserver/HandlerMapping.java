package webserver;

import webserver.http.Request;

import java.util.HashMap;
import java.util.Map;

class HandlerMapping {

    private final Map<RequestMappingInfo, Handler> handlerRegistry;

    HandlerMapping(Map<RequestMappingInfo, Handler> handlers) {
        validateMappingHandler(handlers);
        this.handlerRegistry = new HashMap<>(handlers);
    }

    private void validateMappingHandler(Map<RequestMappingInfo, Handler> requestHandles) {
        long mappingInfoCount = requestHandles.entrySet().size();
        long handlerCount = requestHandles.values().size();
        if (mappingInfoCount != handlerCount) {
            throw new IllegalStateException();
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
