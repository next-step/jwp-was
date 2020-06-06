package webserver;

import http.controller.Controller;
import http.controller.RequestMapper;
import http.parsers.RequestContextParser;
import http.requests.HttpRequest;
import http.responses.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = RequestContextParser.parse(in);
            final HttpResponse httpResponse = new HttpResponse(out);
            final Controller controller = RequestMapper.dispatch(httpRequest);
            controller.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
