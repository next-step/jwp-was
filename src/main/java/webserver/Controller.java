package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

public class Controller {
    private Request request;

    public Controller(Request request) {
        this.request = request;
    }

    public Response serving() throws IOException, URISyntaxException {
        RequestLine requestLine = request.getRequestLine();

        boolean isStaticResource = false;
        String path = requestLine.getPath();

        String[] split = path.split("/");
        if (path.indexOf(".js") != -1 || path.indexOf(".css") != -1) {
            isStaticResource = true;
        }

        String resourcePath = (isStaticResource ? "static" : "") + path.replace(split[0], "");

        Response response = new Response(resourcePath);

        return response;
    }
}
