package webserver;

import controller.Controller;

public class IndexController implements Controller {

    public Response serving(Request request) {
        RequestLine requestLine = request.getRequestLine();

        String path = requestLine.getPath();
        String[] split = path.split("/");

        return new Response("text/html;charset=utf-8", path.replace(split[0], ""));
    }
}
