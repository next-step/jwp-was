package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.Controller;
import webserver.controller.RequestMapping;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            if (in != null) {
                HttpRequest httpRequest = new HttpRequest(in);
                Controller controller = RequestMapping.getController(httpRequest.getPath());
                HttpResponse httpResponse = controller.service(httpRequest);

                write(out, httpResponse);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void write(OutputStream out, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(httpResponse.getBytes());
        dos.flush();
    }

}
