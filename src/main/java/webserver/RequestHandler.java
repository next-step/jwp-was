package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final RequestMapper requestMapper;

    public RequestHandler(Socket connectionSocket, RequestMapper requestMapper) {
        this.connection = connectionSocket;
        this.requestMapper = requestMapper;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = HttpResponse.from(out);


            Controller controller = requestMapper.getController(request.getPath());
            controller.service(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
