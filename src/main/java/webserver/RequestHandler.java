package webserver;

import domain.HttpMethod;
import domain.HttpRequest;
import domain.RequestLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
            final HttpRequest httpRequest = convertToHttpRequest(in);
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

    private HttpRequest convertToHttpRequest(InputStream in) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        return new HttpRequest(br);
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
        final byte[] body = user.toString().getBytes(StandardCharsets.UTF_8);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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
