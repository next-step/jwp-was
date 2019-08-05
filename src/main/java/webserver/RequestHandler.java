package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.HandlerProvider;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final HandlerProvider handlerProvider;

    RequestHandler(final Socket connection,
                   final HandlerProvider handlerProvider) {
        this.connection = connection;
        this.handlerProvider = handlerProvider;
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

            handlerProvider.provide(request)
                    .handle(request, response);
        } catch (final Exception e) {
            logger.error("Error ", e);
        }
    }
}
