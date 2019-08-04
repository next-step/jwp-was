package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.*;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private List<HttpServlet> httpServlets = Arrays.asList(new StaticResourceServlet(),
            new TemplateResourceServlet(),
            new UserCreateServlet(),
            new UserListServlet(),
            new UserLoginServlet());

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = Request.of(in);
            logger.info("IN request: {}", request);

            httpServlets.stream()
                    .filter(it -> it.isMapping(request))
                    .findFirst()
                    .map(serve(request))
                    .orElseGet(Response::notFound)
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
                return Response.internalServerError();
            }
        };
    }
}
