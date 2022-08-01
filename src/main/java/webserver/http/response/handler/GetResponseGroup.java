package webserver.http.response.handler;

import java.util.Map;

import webserver.http.request.handler.HomeRequestHandler;
import webserver.http.request.handler.UserCreateGetRequestHandler;
import webserver.http.request.handler.UserFormRequestHandler;
import webserver.http.request.handler.UserListRequestHandler;

public class GetResponseGroup implements ResponseGroup {
    private final Map<String, ResponseHandler> handlerMap = Map.ofEntries(
            Map.entry(HomeRequestHandler.requestIndex(), new HomeResponseHandler()),
            Map.entry(UserFormRequestHandler.requestIndex(), new UserFormResponseHandler()),
            Map.entry(UserListRequestHandler.requestIndex(), new UserListResponseHandler()),
            Map.entry(UserCreateGetRequestHandler.requestIndex(), new UserCreateGetResponseHandler())
    );

    @Override
    public ResponseHandler getResponse(String index) {
        return handlerMap.getOrDefault(index, new DefaultResponseHandler());
    }
}
