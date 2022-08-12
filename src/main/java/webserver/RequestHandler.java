package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.exception.NotFoundException;
import webserver.http.view.ErrorViewResolver;
import webserver.http.view.View;
import webserver.http.view.ViewResolver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

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
            process(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(InputStream in, OutputStream out) throws Exception {
        HttpResponse response = new HttpResponse(out);
        HttpRequest request = null;
        try {
            request = new HttpRequest(in);

            if (request.getCookies().getCookie("JSESSIONID") == null) {
                response.addHeader("Set-Cookie", "JSESSIONID=" + UUID.randomUUID());
            }

            Controller controller = RequestMapping.getController(request.getPath());
            controller.service(request, response);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            responseError(request, response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

            responseError(request, response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void responseError(HttpRequest request, HttpResponse response, HttpStatus notFound) throws Exception {
        response.updateStatus(notFound);

        ViewResolver viewResolver = new ErrorViewResolver();
        View view = viewResolver.resolveView("");
        view.render(request, response);
    }
}
