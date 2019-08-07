package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.mapping.HandlerMapping;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HandlerMapping handlerMapping;

    public RequestHandler(Socket connectionSocket, HandlerMapping handlerMapping) {
        this.connection = connectionSocket;
        this.handlerMapping = handlerMapping;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(dos);

            handlerMapping.route(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
