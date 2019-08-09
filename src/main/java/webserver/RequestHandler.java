package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.ControllerProvider;
import webserver.http.CookieName;
import webserver.http.HeaderName;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.Response;
import webserver.http.session.Session;
import webserver.http.session.SessionStore;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ControllerProvider controllerProvider;
    private final SessionStore sessionStore;

    RequestHandler(final Socket connection,
                   final ControllerProvider controllerProvider,
                   final SessionStore sessionStore) {
        this.connection = connection;
        this.controllerProvider = controllerProvider;
        this.sessionStore = sessionStore;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! [ConnectedIP={}, Port={}]",
                connection.getInetAddress(), connection.getPort());

        try (connection;
             final InputStream in = connection.getInputStream();
             final OutputStream out = connection.getOutputStream();
             final Response response = HttpResponse.of(out)) {

             final HttpRequest request = HttpRequest.of(in);
            request.setSession(getSession(request, response));

            logger.debug("In request [request={}]", request);

            controllerProvider.provide(request)
                    .service(request, response);
        } catch (final Exception e) {
            logger.error("Error ", e);
        }
    }

    private Session getSession(final HttpRequest request,
                               final Response response) {
        return request.getCookies()
                .getString(CookieName.SESSION_ID.toString())
                .map(sessionStore::getSession)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseGet(() -> {
                    final Session newSession = sessionStore.newSession();
                    response.addHeader(HeaderName.SET_COOKIE, CookieName.SESSION_ID + "=" + newSession.getId());

                    return newSession;
                });
    }
}
