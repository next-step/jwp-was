package webserver;

import http.request.HttpRequest;
import http.request.parser.RequestReader;
import http.response.HttpResponse;
import http.response.sequelizer.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            HttpRequest httpRequest = RequestReader.read(in);
            HttpResponse httpResponse = new HttpResponse();

            String path = httpRequest.getPath();
            Controller controller = FrontController.controllerMapping(path);
            controller.service(httpRequest, httpResponse);

            ResponseWriter.write(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
