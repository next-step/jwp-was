package webserver;

import utils.FileIoUtils;
import webserver.http.*;

import java.util.Map;

public class ResourceHandler implements Handler {

    @Override
    public boolean isSupport(Request request) {
        Headers headers = request.getHeaders();

        String contentType = headers.getValue("Accept");

        return contentType.contains("text/html");
    }

    @Override
    public Response handle(Request request) {
        try {
            String path = request.getPath();

            byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/" + path);

            Headers headers = Headers.of(Map.of(
                    "Content-Type", "text/html;charset=utf-8",
                    "Content-Length", String.valueOf(bytes.length)
            ));

            StatusLine statusLine = new StatusLine(ProtocolVersion.HTTP11, Status.OK);
            return new Response(statusLine, headers, bytes);
        } catch (Exception e) {
            return null;
        }
    }

}
