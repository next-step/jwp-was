package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestStream;

import java.io.DataOutputStream;
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
            DataOutputStream dos = new DataOutputStream(out);
            RequestStream requestStream = new RequestStream(in);
            HttpRequest httpRequest = HttpRequest.parse(requestStream);
            logger.debug("Request {}", httpRequest.getRequestLine());

            HttpResponse httpResponse = HttpResponse.createResponse(httpRequest);
            httpResponse.responseByStatus(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
