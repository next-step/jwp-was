package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static utils.IOUtils.readLines;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = new Request(readLines(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))));
            String path = getPathFromRequest(request);
            logger.debug("requestPath : {}", path);

            Response response = new Response(out, path);

            logger.debug("request : {}", request.getHeader());
            byte[] body = response.getBody(path);

            response200Header(response, body.length);
            responseBody(response, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String getPathFromRequest(Request request) {
        return request.getRequestPath();
    }

    private void response200Header(Response response, int lengthOfBodyContent) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes(StringUtils.join(response.getHeader(), ": "));
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(Response response, byte[] body) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
