package webserver.service;

import webserver.request.header.RequestHeader;
import webserver.response.ResponseHeaderWriter;
import webserver.response.post.PostUserCreateResponse;
import webserver.service.exception.InvalidRequestException;

public class ResponsePostHandler {
    private static final String USER_CREATE = "/user/create";

    public String handle(RequestHeader header, String requestBody) {
        ResponseHeaderWriter responseHeaderWriter = new ResponseHeaderWriter();

        if (header.index().equals(USER_CREATE)) {
            PostUserCreateResponse response = new PostUserCreateResponse();
            response.response(requestBody);
            return responseHeaderWriter.response302(header.protocolVersion(), header.host());
        }

        throw new InvalidRequestException("제공되지 않은 요청입니다.");
    }
}
