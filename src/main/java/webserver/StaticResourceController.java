package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController implements Controller {

    public Response serving(Request request) throws IOException, URISyntaxException {
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getPath();

        String[] split = path.split("/");

        if (path.indexOf(".ico") != -1) {
            return new Response(path.replace(split[0], ""));
        }

        return new Response("static" + path.replace(split[0], ""));
    }
}
