package webserver.http.response.handler.get;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.handler.ResponseHandler;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class DefaultResponseHandler implements ResponseHandler {

    private static final String DEFAULT_INDEX = "/index.html";

    @Override
    public ResponseHeader run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.response(requestHeader.uri()))
                .addContentLength(responseBody.length)
                .addLocation(DEFAULT_INDEX);
    }
}
