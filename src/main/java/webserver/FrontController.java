package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.HttpRequest;
import webserver.domain.ResponseEntity;
import webserver.handlers.RequestHandler;
import webserver.handlers.ResponseHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FrontController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    private final Socket connection;

    private final RequestHandler requestHandler;

    private final ResponseHandler responseHandler;

    public FrontController(Socket connectionSocket, RequestHandler requestHandler, ResponseHandler responseHandler) {
        this.connection = connectionSocket;
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             OutputStream out = connection.getOutputStream();
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out))) {

            HttpRequest httpRequest = HttpRequest.newInstance(br);
            logger.info("Http Request: {}", httpRequest);

            ResponseEntity<?> response = requestHandler.handle(httpRequest);

            logger.info("Http Response: {}", response.getHeaders());
            responseHandler.changeWriter(wr).handle(response);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
