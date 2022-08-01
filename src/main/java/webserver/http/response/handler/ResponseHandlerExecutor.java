package webserver.http.response.handler;

import java.util.Map;

import webserver.http.request.header.RequestHeader;
import webserver.http.HttpMethod;

public class ResponseHandlerExecutor {
    private final Map<HttpMethod, ResponseGroup> responseMap = Map.ofEntries(
            Map.entry(HttpMethod.GET, new GetResponseGroup()),
            Map.entry(HttpMethod.POST, new PostResponseGroup())
    );

    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        ResponseGroup responseGroup = responseMap.get(requestHeader.httpMethod());
        ResponseHandler handler = responseGroup.getResponse(requestHeader.index());
        return handler.run(requestHeader, requestBody, responseBody);
    }
}
