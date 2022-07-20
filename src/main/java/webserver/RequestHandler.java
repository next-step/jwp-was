package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import controller.RequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import service.RequestService;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private RequestController requestController;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            Request request = IOUtils.convertRequest(in);
            requestController = new RequestController(request, new RequestService(request));
            requestController.handle(out);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
