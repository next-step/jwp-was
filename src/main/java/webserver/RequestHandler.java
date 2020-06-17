package webserver;

import Controller.Controller;
import Controller.ControllerMapper;
import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = makeHttpResponse(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);
            dos.write(httpResponse.makeHttpResponseBytes(), 0, httpResponse.makeHttpResponseBytes().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Nonnull
    private HttpResponse makeHttpResponse(@Nonnull HttpRequest httpRequest) {
        Controller controller = ControllerMapper.getController(httpRequest.getRequestLine().getPath());
        if (controller != null) {
            return controller.service(httpRequest);
        }

        return HttpResponse.from(httpRequest, HttpStatus.OK);
    }
}
