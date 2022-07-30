package webserver;

import webserver.http.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HandlerMapping {

    private final Map<RequestMappingInfo, Handler> handlerRegistry;

    public HandlerMapping(Handler... handlers) {
        this.handlerRegistry = createRequestMappingHandlers(List.of(handlers));
    }

    private Map<RequestMappingInfo, Handler> createRequestMappingHandlers(List<Handler> handlers) {
        Map<RequestMappingInfo, Handler> mappingHandlers = new HashMap<>();

        for (Handler handler : handlers) {
            validateMappingHandler(mappingHandlers, handler);

            mappingHandlers.put(handler.getMappingInfo(), handler);
        }

        return mappingHandlers;
    }

    private void validateMappingHandler(Map<RequestMappingInfo, Handler> requestHandles, Handler handler) {
        if (requestHandles.containsKey(handler.getMappingInfo())) {
            throw new IllegalStateException("이미 등록된 Request Mapping 입니다. " + handler.getMappingInfo());
        }
    }

    public Handler getHandler(Request request) {
        return handlerRegistry.get(new RequestMappingInfo(request.getPath(), request.getMethod()));
    }
}
