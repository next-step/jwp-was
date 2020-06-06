package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = httpRequestHandler.handle(httpRequest);


            httpResponse.response(out);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
