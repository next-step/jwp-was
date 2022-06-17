package controller;

import webserver.Request;
import webserver.RequestLine;
import webserver.Response;

public class StaticResourceController implements Controller {

    public Response serving(Request request) {
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getPath();

        String[] split = path.split("/");

        if (path.indexOf(".ico") != -1 || path.indexOf(".html") != -1) {
            return new Response(request.getContentType(), path.replace(split[0], ""));
        }

//        if (path.indexOf(".css") != -1) {
//            return new Response("text/css", "static" + path.replace(split[0], ""));
//        }

        return new Response(request.getContentType(), "static" + path.replace(split[0], ""));
    }
}
