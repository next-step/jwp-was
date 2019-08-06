package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpProcessor;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static webserver.http.HttpRequest.createHtpRequest;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpProcessor httpProcessor;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpProcessor = new HttpProcessor();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = createHtpRequest(in);
            httpProcessor.process(httpRequest, new HttpResponse(dos));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
