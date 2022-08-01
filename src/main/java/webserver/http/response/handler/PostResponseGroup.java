package webserver.http.response.handler;

import java.util.Map;

import webserver.http.request.handler.post.UserCreatePostRequestHandler;
import webserver.http.request.handler.post.UserLoginRequestHandler;
import webserver.http.request.handler.exception.InvalidRequestException;
import webserver.http.response.handler.post.UserCreatePostResponseHandler;
import webserver.http.response.handler.post.UserLoginResponseHandler;

public class PostResponseGroup implements ResponseGroup {
    private final Map<String, ResponseHandler> handlerMap = Map.ofEntries(
            Map.entry(UserCreatePostRequestHandler.requestIndex(), new UserCreatePostResponseHandler()),
            Map.entry(UserLoginRequestHandler.requestIndex(), new UserLoginResponseHandler())
    );

    @Override
    public ResponseHandler getResponse(String index) {
        if (handlerMap.containsKey(index)) {
            return handlerMap.get(index);
        }
        throw new InvalidRequestException("지원되지 않는 요청입니다.");
    }
}
