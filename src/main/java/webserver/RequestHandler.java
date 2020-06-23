package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerInitializer;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseProcessor;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.of(br);
            HttpResponse httpResponse = new HttpResponse();
            if (httpRequest.isStaticFileRequest()) {
                HttpResponseProcessor.init(httpRequest, httpResponse);
            } else {
                Controller controller = ControllerInitializer.get(httpRequest.getPath());
                ModelAndView mav = controller.service(httpRequest, httpResponse);
                HttpResponseProcessor.init(mav, httpRequest, httpResponse);
            }
            httpResponse.response(dos);
            dos.flush();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
