package webserver;

import http.HttpRequest;
import http.HttpRequestProcessor;
import http.HttpResponse;
import http.controller.Controller;
import http.view.DataOutputStreamView;
import http.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            final HttpRequest request = HttpRequestProcessor.createRequest(in);
            final HttpResponse response = new HttpResponse();
            final View view = new DataOutputStreamView(out);

            final Controller controller = RequestMapping.getController(request.getPath());
            controller.service(request, response);

            view.render(response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
