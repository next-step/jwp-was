package webserver;

import controller.Controller;
import controller.ControllerMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

@Slf4j
public class RequestHandler implements Runnable {

    private static final ControllerMapper CONTROLLER_MAPPER = new ControllerMapper();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(inputStream);
            HttpResponse httpResponse = HttpResponse.of(outputStream, httpRequest);

            if (httpRequest.hasPathFileExtension()) {
                byte[] responseBody = FileIoUtils.loadFileFromClasspath(httpRequest.getFilePath());

                httpResponse.updateResponseBodyContent(responseBody);
                httpResponse.flush();
                return;
            }

            Controller controller = CONTROLLER_MAPPER.findController(httpRequest.getUri());
            controller.execute(httpRequest, httpResponse);

            httpResponse.flush();
        } catch (IOException | URISyntaxException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }
}
