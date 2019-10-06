package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.http.mapping.RequestMapping;
import webserver.http.mapping.ResourceMapping;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if (in == null) {
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            handleRequest(br, out);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void handleRequest(BufferedReader br, OutputStream out) throws Exception {
        HttpRequest httpRequest = HttpRequest.of(br);
        String requestUri = httpRequest.getRequestUri();
        log.debug("requestUri : {}", requestUri);

        handleResources(out, requestUri);
        handleController(out, httpRequest, requestUri);
    }

    private void handleResources(OutputStream out, String requestUri) {
        if (!ResourceMapping.support(requestUri)) {
            return;
        }

        HttpResponse response = HttpResponseResolver.resource(requestUri);
        ResponseHandler.response(out, response);
    }

    private void handleController(OutputStream out, HttpRequest httpRequest, String requestUri) {
        if (!RequestMapping.support(requestUri)) {
            return;
        }

        Controller controller = RequestMapping.getController(requestUri);
        HttpResponse response = controller.handle(httpRequest);
        ResponseHandler.response(out, response);
    }
}
