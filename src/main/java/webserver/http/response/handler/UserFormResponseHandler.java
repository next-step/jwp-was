package webserver.http.response.handler;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class UserFormResponseHandler implements ResponseHandler {
    private static final String REDIRECT_INDEX_HTML = "/user/form.html";
    @Override
    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        ResponseHeader responseHeader = new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK);
        return responseHeader.addContentType(ContentType.HTML)
                .addLocation(REDIRECT_INDEX_HTML)
                .toString();
    }
}
