package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = HttpRequestParser.parse(in);
            final HttpResponse httpResponse = handle(httpRequest);
            new HttpResponseWriter(out).write(httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private HttpResponse handle(HttpRequest httpRequest) {
        final Controller controller = RequestMapping.getController(httpRequest.getPath());
        if (controller != null) {
            return controller.service(httpRequest);
        }
        return HttpResponse.forward(httpRequest.getPath());
    }
}
