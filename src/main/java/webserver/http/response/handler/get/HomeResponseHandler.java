package webserver.http.response.handler.get;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.handler.ResponseHandler;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class HomeResponseHandler implements ResponseHandler {
    private static final String LOCATION = "/index.html";

    @Override
    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.response(requestHeader.index()))
                .addContentLength(responseBody.length)
                .addLocation(LOCATION)
                .toString();
    }
}
