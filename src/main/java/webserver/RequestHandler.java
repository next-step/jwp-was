package webserver;

import http.HttpRequest;
import http.HttpRequestProcessor;
import http.HttpResponse;
import http.HttpSessionManager;
import http.controller.Controller;
import http.view.DataOutputStreamView;
import http.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final HttpSessionManager sessionManager;

    public RequestHandler(Socket connectionSocket, HttpSessionManager sessionManager) {
        this.connection = connectionSocket;
        this.sessionManager = sessionManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            final HttpRequest request = HttpRequestProcessor.createRequest(in);
            request.setSessionManager(sessionManager);
            final HttpResponse response = new HttpResponse();
            final View view = new DataOutputStreamView(out);

            String sessionId = request.getCookie(HttpSessionManager.SESSION_NAME);
            if (Strings.isEmpty(sessionId)) {
                initializeSession(request, response);
            }

            final Controller controller = RequestMapping.getController(request.getPath());
            controller.service(request, response);

            view.render(response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void initializeSession(HttpRequest request, HttpResponse response) {
        String newSessionId = sessionManager.createSession();
        response.addCookie(HttpSessionManager.SESSION_NAME, newSessionId);
        response.addCookiePath("/");
        request.addCookie(HttpSessionManager.SESSION_NAME, newSessionId);
    }
}
