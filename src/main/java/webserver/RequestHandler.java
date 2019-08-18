package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.http.mapping.RequestMapping;
import webserver.http.mapping.ResourceMapping;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;
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
            if (in == null) {
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            handleRequest(br, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(BufferedReader br, OutputStream out) throws Exception {
        HttpRequest httpRequest = HttpRequest.of(br);
        String requestUri = httpRequest.getRequestUri();
        logger.debug("requestUri : {}", requestUri);

        if (ResourceMapping.support(requestUri)) {
            HttpResponse response = new HttpResponse().resource(requestUri);
            ResponseHandler.response(out, response);
            return;
        }

        if (RequestMapping.support(requestUri)) {
            Controller controller = RequestMapping.getController(requestUri);
            controller.handle(out, httpRequest);
        }
    }
}
