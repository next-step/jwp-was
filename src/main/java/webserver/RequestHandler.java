package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerMatcher;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ROOT = "/";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            String path = getDefaultPath(request.getPath());
            Controller controller = ControllerMatcher.matchController(path);
            if (controller == null) {
                HttpResponse notFoundResponse = HttpResponse.NOT_FOUND;
                writeResponse(notFoundResponse, new DataOutputStream(out));
                return;
            }
            HttpResponse response = controller.service(request);
            writeResponse(response, new DataOutputStream(out));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDefaultPath(String path) {
        if (ROOT.equals(path)) {
            return "/index.html";
        }
        return path;
    }

    private void writeResponse(HttpResponse response, DataOutputStream dos) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %s \r%n", response.getHttpStatusCode()));
            writeHeaders(response, dos);
            writeCookies(response, dos);
            dos.writeBytes("\r\n");
            dos.write(response.body(), 0, response.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders(HttpResponse response, DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : response.headerEntries()) {
            dos.writeBytes(String.format("%s: %s\r%n", entry.getKey(), entry.getValue()));
        }
    }

    private void writeCookies(HttpResponse response, DataOutputStream dos) throws IOException {
        if (response.cookieEntries().isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : response.cookieEntries()) {
            String cookieValue = entry.getKey() + "=" + entry.getValue();
            dos.writeBytes(String.format("%s: %s\r%n", HttpHeader.SET_COOKIE, cookieValue));
        }
    }
}
