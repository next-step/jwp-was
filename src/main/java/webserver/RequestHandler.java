package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.RequestLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_PATH = "./templates";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = new HttpRequest(in);
            final RequestLine requestLine = httpRequest.makeRequestLine();
            LOGGER.debug(requestLine.toString());

            final DataOutputStream dos = new DataOutputStream(out);
            if (requestLine.getHttpMethod() == HttpMethod.POST && "/user/create".equals(requestLine.getPath())) {
                responseForPost(httpRequest, dos);
                return;
            }
            responseForGet(requestLine, dos);
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseForGet(RequestLine requestLine, DataOutputStream dos) throws IOException, URISyntaxException {
        final byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + requestLine.getPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void responseForPost(HttpRequest httpRequest, DataOutputStream dos) {
        final Map<String, String> payloads = httpRequest.getPayloads();
        final User user = new User(
                payloads.get("userId"),
                payloads.get("password"),
                payloads.get("name"),
                payloads.get("email")
        );
        LOGGER.debug(user.toString());
        response302Header(dos);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
