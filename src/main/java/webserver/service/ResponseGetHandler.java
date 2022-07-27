package webserver.service;

import java.io.IOException;
import java.util.Set;

import webserver.request.header.RequestHeader;
import webserver.response.HttpResponseStatus;
import webserver.response.get.GetUserFormHtmlResponse;
import webserver.response.header.ContentType;
import webserver.response.header.ResponseHeader;

public class ResponseGetHandler {
    private static final String GET_INDEX_HTML = "/index.html";
    private static final String GET_USER_FORM_HTML = "/user/form.html";
    private static final String USER_CREATE = "/user/create";
    private static final Set<String> AVAILABLE_INDEX = Set.of(
            GET_INDEX_HTML,
            GET_USER_FORM_HTML
    ) ;

    public String handle(RequestHeader requestHeader, int lengthOfBodyContent) throws IOException {
        if (isAvailableRequest(requestHeader.index())) {
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                    .addContentType(ContentType.HTML)
                    .addContentLength(lengthOfBodyContent).toString();
        }

        if (requestHeader.index().equals(USER_CREATE)) {
            GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();
            response.response(requestHeader);
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                    .addContentType(ContentType.HTML)
                    .addContentLength(lengthOfBodyContent)
                    .addLocation(GET_INDEX_HTML)
                    .toString();

        }

        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addLocation(GET_INDEX_HTML)
                .toString();
    }

    private boolean isAvailableRequest(String index) {
        return AVAILABLE_INDEX.contains(index);
    }
}
