package webserver.http;

import utils.FileIoUtils;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

public class Controller {

    public Response service(Request request) {
        try {
            String path = request.getPath();

            byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/" + path);

            StatusLine statusLine = new StatusLine(ProtocolVersion.HTTP11, Status.OK);

            Headers headers = Headers.of(Map.of(
                    "Content-Type", "text/html;charset=utf-8",
                    "Content-Length", String.valueOf(bytes.length)
            ));

            return new Response(statusLine, headers, bytes);
        } catch (Exception e) {
            return null;
        }
    }

}
