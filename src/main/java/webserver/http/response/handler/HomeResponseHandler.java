package webserver.http.response.handler;

import webserver.http.request.RequestIndex;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class HomeResponseHandler implements ResponseHandler{
    @Override
    public String run(RequestHeader requestHeader, int lengthOfBodyContent) {
        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.response(requestHeader.index()))
                .addContentLength(lengthOfBodyContent)
                .addLocation(RequestIndex.GET_INDEX_HTML.getPath())
                .toString();
    }
}
