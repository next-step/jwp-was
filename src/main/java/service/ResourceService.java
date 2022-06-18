package service;

import java.io.File;
import java.io.IOException;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeader;

public class ResourceService {
    private ResourceService() {}

    public static Response getResource(Request request) throws IOException {
        ResponseBody responseBody = ResponseBody.from(
                new File("./webapp" + request.getPath())
        );
        ResponseHeader responseHeader = new ResponseHeader()
                .setContentType(request.getContentType())
                .setContentLength(responseBody.getContentLength());
        return new Response(responseHeader, responseBody);
    }
}
