package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.controller.Controller;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;
import webserver.http.domain.RequestMapping;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);
            RequestMapping requestMapping = new RequestMapping();

            DataOutputStream dos = new DataOutputStream(out);
            Controller controller = requestMapping.controller(httpRequest.path());
            HttpResponse httpResponse = new HttpResponse(dos);
            if (controller != null) {
                controller.execute(httpRequest, httpResponse);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
