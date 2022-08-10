package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Path;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import static webserver.http.HttpSession.JSESSIONID;

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
            HttpRequest httpRequest = new HttpRequest(in);
            Path path = httpRequest.getPath();
            HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.getCookies().getCookie(JSESSIONID) == null) {
                httpResponse.addHeader("Set-Cookie", "JSESSIONID=" + UUID.randomUUID());
            }

            Controller controller = RequestMapping.getController(httpRequest);
            if (controller == null) {
                httpResponse.forward(path.getPath());
                return;
            }
            controller.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
