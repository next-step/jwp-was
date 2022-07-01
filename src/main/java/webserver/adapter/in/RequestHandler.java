package webserver.adapter.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.adapter.in.controller.Controller;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.HttpSession;
import webserver.domain.http.HttpSessions;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final UserProcessor userProcessor;

    public RequestHandler(Socket connectionSocket, UserProcessor userProcessor) {
        this.connection = connectionSocket;
        this.userProcessor = userProcessor;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            if (Objects.isNull(httpRequest.getHttpHeader().getCookies().get(HttpSession.SESSION_ID))) {
                HttpSession session = HttpSessions.create();
                httpResponse.addSessionCookie(session.getId());
            }

            String path = httpRequest.getUri().getPath();
            Controller controller = new RequestMapper(userProcessor).getController(path);

            if (Objects.nonNull(controller)) {
                controller.service(httpRequest, httpResponse);
                return;
            }

            httpResponse.responseForward(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
