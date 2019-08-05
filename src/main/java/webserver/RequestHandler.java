package webserver;

import controller.Controller;
import controller.UserController;
import exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ResourceViewResolver;
import view.View;
import view.ViewResolver;
import webserver.http.HttpVersion;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(inputStream);
            HttpResponse response = new HttpResponse(HttpVersion.HTTP_1_1);

            Controller userController = UserController.getInstance();
            String path = userController.get(request, response);

            ViewResolver viewResolver = new ResourceViewResolver();
            View view = viewResolver.toView(response, path);

            response.flush(outputStream, view.responseBody());

        } catch (FileNotFoundException e) {
            logger.error("File not found : " + e.getMessage());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
