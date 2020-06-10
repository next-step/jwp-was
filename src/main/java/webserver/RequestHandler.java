package webserver;

import http.HttpRequest;
import http.Response.HttpResponse;
import http.controller.Controller;
import http.view.DataOutputStreamView;
import http.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            View dov = new DataOutputStreamView(out);
            HttpRequest request = new HttpRequest(br);
            HttpResponse response = RequestMapping.getResponse(request.getPath());
            response.setView(dov);

            Controller controller = RequestMapping.getController(request.getPath());
            if (controller != null) {
                controller.service(request, response);
            } else {
                response.response(request.getPath());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
