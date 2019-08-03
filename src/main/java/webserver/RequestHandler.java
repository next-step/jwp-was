package webserver;

import java.io.*;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.handler.Handler;
import webserver.handler.HandlerProvider;
import webserver.http.response.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Handler NOT_FOUND = (ignore, response) -> response.notFound();

    private final Socket connection;
    private final List<HandlerProvider> handlerProviders;

    RequestHandler(final Socket connection,
                   final List<HandlerProvider> handlerProviders) {
        this.connection = connection;
        this.handlerProviders = handlerProviders;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! [ConnectedIP={}, Port={}]",
                connection.getInetAddress(), connection.getPort());

        try (connection;
             final InputStream in = connection.getInputStream();
             final OutputStream out = connection.getOutputStream();
             final HttpResponse response = HttpResponse.of(out)) {

             final HttpRequest request = HttpRequest.of(in);
             logger.debug("In request [request={}]", request);

            handlerProviders.stream()
                    .filter(handlerProvider -> handlerProvider.support(request))
                    .findFirst()
                    .map(HandlerProvider::provide)
                    .orElse(NOT_FOUND)
                    .handle(request, response);

        } catch (final Exception e) {
            logger.error("Error ", e);
        }
    }
}
