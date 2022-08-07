package webserver;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.*;
import webserver.http.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            String url = httpRequest.getPath();
            RequestMapping requestMapping = new RequestMapping();
            Controller controller = requestMapping.getController(url);
            if (controller == null) {
                httpResponse.forward(url);
                return;
            }
            controller.service(httpRequest, httpResponse);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
