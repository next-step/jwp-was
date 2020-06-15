package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestReader;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.HttpSessionManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = HttpRequestReader.read(in);
            HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.getHeader(HttpSessionManager.SESSION_ID) == null) {
                String uuid = HttpSessionManager.createSession();
                httpResponse.addHeader("Set-Cookie", HttpSessionManager.getCookieHeader(uuid));
            }

            RequestMappingManager.execute(httpRequest, httpResponse);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
