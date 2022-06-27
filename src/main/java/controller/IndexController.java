package controller;

import webserver.MediaType;
import webserver.Request;
import webserver.RequestLine;
import webserver.Response;

public class IndexController implements Controller {

    public Response service(Request request) {
        RequestLine requestLine = request.getRequestLine();

        String path = requestLine.getPath();
        String[] split = path.split("/");

        return new Response(MediaType.TEXT_HTML_UTF8, path.replace(split[0], ""), null);
    }
}
