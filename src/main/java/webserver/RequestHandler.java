package webserver;

import controller.DispatchController;
import exception.ResourceNotFoundException;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final DispatchController dispatchController = new DispatchController();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            HttpRequest request = HttpRequest.of(bufferedReader);
            handle(request,dos);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest request, DataOutputStream dos) throws IOException, URISyntaxException {
        dispatchController.handleRequest(request, dos);
    }

}
