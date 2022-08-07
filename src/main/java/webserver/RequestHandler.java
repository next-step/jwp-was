package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.Controller;
import webserver.controller.RequestMapping;
import webserver.request.HttpRequest;
import webserver.request.Method;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;
import webserver.util.HandlebarsObject;


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
            if (in != null) {
                HttpRequest httpRequest = new HttpRequest(in);
                HttpResponse httpResponse = new HttpResponse(out);
                Controller controller = RequestMapping.getController(httpRequest.getPath());
                if (controller != null) {
                    controller.service(httpRequest, httpResponse);
                } else {
                    String path = httpRequest.getPath();
                    if (path.endsWith("html")) {
                        httpResponse.forward(IOUtils.loadFileFromClasspath("./templates" + path));
                    } else if (path.endsWith("css")) {
                        httpResponse.forwardCSS(IOUtils.loadFileFromClasspath("./static" + path));
                    }
                }
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
