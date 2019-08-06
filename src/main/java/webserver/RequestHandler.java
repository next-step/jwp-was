package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.ResponseFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.function.Function;

import static webserver.response.ResponseFactory.internalServerError;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private ServletContext servletContext = new ServletContext();

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = HttpRequest.newInstance(in);

            logger.info("IN request: {}", request);

            servletContext.mapping(request)
                    .map(serve(request))
                    .orElseGet(ResponseFactory::notFound)
                    .send(out);

        } catch (Exception e) {
            logger.error("uncaught error", e);
        }
    }

    private Function<HttpServlet, Response> serve(Request request) {
        return servlet -> {
            try {
                return servlet.service(request);
            } catch (Exception e) {
                logger.error("servlet error", e);
                return internalServerError();
            }
        };
    }
}
