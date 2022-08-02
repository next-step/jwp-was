package webserver.http.response.handler;

import java.util.Map;

import webserver.http.request.handler.get.HomeRequestHandler;
import webserver.http.request.handler.get.UserCreateByQueryParamRequestHandler;
import webserver.http.request.handler.get.UserFormRequestHandler;
import webserver.http.request.handler.get.UserListRequestHandler;
import webserver.http.response.handler.get.DefaultResponseHandler;
import webserver.http.response.handler.get.HomeResponseHandler;
import webserver.http.response.handler.get.UserCreateGetResponseHandler;
import webserver.http.response.handler.get.UserFormResponseHandler;
import webserver.http.response.handler.get.UserListResponseHandler;

public class GetResponseGroup implements ResponseGroup {
    private final Map<String, ResponseHandler> handlerMap = Map.ofEntries(
            Map.entry(HomeRequestHandler.requestIndex(), new HomeResponseHandler()),
            Map.entry(UserFormRequestHandler.requestIndex(), new UserFormResponseHandler()),
            Map.entry(UserListRequestHandler.requestIndex(), new UserListResponseHandler()),
            Map.entry(UserCreateByQueryParamRequestHandler.requestIndex(), new UserCreateGetResponseHandler())
    );

    @Override
    public ResponseHandler getResponse(String index) {
        return handlerMap.getOrDefault(index, new DefaultResponseHandler());
    }
}
