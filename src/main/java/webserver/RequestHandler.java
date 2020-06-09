package webserver;

import http.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSessionStorage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final HttpSessionStorage httpSessionStorage = new LocalHttpSessionStorage();

    private final RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping();
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection
            .getOutputStream()) {

            HttpRequest httpRequest = HttpRequest.from(in, httpSessionStorage);
            HttpResponse httpResponse = requestHandlerMapping.handle(httpRequest);

            HttpSession httpSession = httpRequest.getHttpSession();
            httpResponse.addCookie(HttpSessionStorage.SESSION_ID_NAME, httpSession.getId());

            httpResponse.response(out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
