package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpStatusResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpStatusResponse.class);

    public static HttpResponse responseBadRequest400() {
        return HttpResponse.createBadRequest();
    }

    public static void responseBodRequest400(DataOutputStream dos) {
        final String badRequest = "Bod Request";
        byte[] body = badRequest.getBytes(StandardCharsets.UTF_8);

        try {
            dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
