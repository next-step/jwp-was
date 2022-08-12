package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import controller.Controller;
import model.HttpRequest;
import model.HttpResponse;
import model.RequestMappingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final RequestMapper mapper;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.mapper = new RequestMapper();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            final HttpRequest httpRequest = new HttpRequest(in);
            final HttpResponse response = new HttpResponse(out);
            logger.debug("request : {}", httpRequest);

            Controller controller = mapper.mapping(new RequestMappingInfo(httpRequest));
            controller.service(httpRequest, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
