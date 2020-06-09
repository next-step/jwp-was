package webserver;

import controller.Controller;
import controller.ControllerMapper;
import http.request.HttpRequest;
import http.request.requestline.path.Path;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

@Slf4j
public class RequestHandler implements Runnable {

    private static final String NEW_LINE = "\r\n";
    private static final ControllerMapper CONTROLLER_MAPPER = new ControllerMapper();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(outputStream);

            HttpRequest httpRequest = HttpRequest.from(inputStream);
            if (httpRequest.hasPathFileExtension()) {
                byte[] responseBody = FileIoUtils.loadFileFromClasspath(httpRequest.getFilePath());

                response200Header(dos, httpRequest.getMimeType(), responseBody.length);
                responseBody(dos, responseBody);
                return;
            }

            Path requestPath = httpRequest.getPath();
            Controller controller = CONTROLLER_MAPPER.findController(requestPath.getUri());
            controller.execute(httpRequest);

            response200Header(dos);
        } catch (IOException | URISyntaxException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos) {
        response200Header(dos, "text/html", 0);
    }

    private void response200Header(DataOutputStream dos, String mimeType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + NEW_LINE);
            dos.writeBytes(String.format("Content-Type: %s", mimeType) + NEW_LINE);
            dos.writeBytes(String.format("Content-Length: %s", lengthOfBodyContent) + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
