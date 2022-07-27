package webserver.service;

import java.io.IOException;
import java.util.Set;

import webserver.request.header.RequestHeader;
import webserver.response.ResponseHeaderWriter;
import webserver.response.get.GetUserFormHtmlResponse;

public class ResponseGetHandler {
    private static final String GET_INDEX_HTML = "/index.html";
    private static final String GET_USER_FORM_HTML = "/user/form.html";
    private static final String USER_CREATE = "/user/create";
    private static final Set<String> AVAILABLE_INDEX = Set.of(
            GET_INDEX_HTML,
            GET_USER_FORM_HTML
    ) ;

    public String handle(RequestHeader requestHeader, int lengthOfBodyContent) throws IOException {
        ResponseHeaderWriter responseHeaderWriter = new ResponseHeaderWriter();

        if (isAvailableRequest(requestHeader.index())) {
            return responseHeaderWriter.response200(requestHeader.protocolVersion(), lengthOfBodyContent);
        }

        if (requestHeader.index().equals(USER_CREATE)) {
            GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();
            response.response(requestHeader);
            return responseHeaderWriter.response200(requestHeader.protocolVersion(), lengthOfBodyContent);
        }

        return responseHeaderWriter.response200(requestHeader.protocolVersion(), lengthOfBodyContent);
    }
    private boolean isAvailableRequest(String index) {
        return AVAILABLE_INDEX.contains(index);
    }
}
