package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.exception.MethodNotAllowedException;
import webserver.http.exception.NotFoundException;
import webserver.http.exception.NotImplementedException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String ROOT_PATH = "/";
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            process(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(InputStream in, OutputStream out) {
        HttpResponse response = new HttpResponse(out);
        try {
            HttpRequest request = new HttpRequest(in);

            String path = getDefaultPath(request.getPath());

            Controller controller = RequestMapping.getController(path);

            if (controller == null) {
                response.forward(path);
                return;
            }
            controller.service(request, response);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            response.forwardError(HttpStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            logger.error(e.getMessage());
            response.forwardError(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (NotImplementedException e) {
            logger.error(e.getMessage());
            response.forwardError(HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            response.forwardError(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getDefaultPath(String path) {
        if (ROOT_PATH.equals(path)) {
            return "/index.html";
        }
        return path;
    }
}
