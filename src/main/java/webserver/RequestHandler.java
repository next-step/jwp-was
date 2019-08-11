package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestFactory;
import webserver.resolver.RequestResolver;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestResolver resolver = new RequestResolver();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.create(in);
            HttpResponse httpResponse = new HttpResponse(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);
            resolver.resolve(httpRequest, httpResponse);

            httpResponse.response(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
