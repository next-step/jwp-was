package webserver;

import controller.Controller;
import controller.RequestMapping;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class RequestHandler implements Runnable {

    private static final RequestMapping CONTROLLER_MAPPER = new RequestMapping();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(inputStream);

            Controller controller = CONTROLLER_MAPPER.findController(httpRequest.getUri());
            HttpResponse httpResponse = controller.service(httpRequest);

            httpResponse.flush(outputStream);
        } catch (IOException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }
}
