package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import webserver.controller.Controller;
import webserver.http.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.model.request.Extension;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            movePage(new HttpRequest(in), new HttpResponse(out));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void movePage(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isStaticResource()) {
            httpResponse.responseStaticResource(httpRequest, httpResponse, FileIoUtils.loadFileFromClasspath(httpRequest.responsePath()));
            return;
        }

        HandlerMapping handlerMapping = new HandlerMapping();
        Controller controller = handlerMapping.getControllerMap().get(httpRequest.getPath());
        controller.service(httpRequest, httpResponse);
    }
}
