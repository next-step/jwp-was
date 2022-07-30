package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.Controller;
import webserver.handler.RequestMapping;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.response.HttpResponse;
import webserver.session.HttpSessionIdHolder;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String SESSION_COOKIE_NAME = "JWP_SESSION_ID";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            final DataOutputStream dos = new DataOutputStream(out);

            final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);
            final Controller controller = RequestMapping.map(httpRequest.getRequestLine());
            final HttpResponse httpResponse = controller.handle(httpRequest)
                .addCookie(SESSION_COOKIE_NAME, HttpSessionIdHolder.getSessionId());

            HttpSessionIdHolder.invalidate();

            httpResponse.write(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
