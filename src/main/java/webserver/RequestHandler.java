package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ServletContext servletContext;

    RequestHandler(Socket connection, ServletContext servletContext) {
        this.connection = connection;
        this.servletContext = servletContext;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = HttpRequest.newInstance(in);
            logger.info("IN request: {}", request);

            Response mapping = servletContext.mapping(request);
            mapping.setCookie("JSESSIONID=" + request.getSession().getId());
            mapping.send(out);
        } catch (Exception e) {
            logger.error("uncaught error", e);
        }
    }
}
