package was;

import mvc.RequestMapping;
import mvc.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestMapping requestMapping;

    public RequestHandler(Socket connectionSocket, RequestMapping requestMapping) {
        this.connection = connectionSocket;
        this.requestMapping = requestMapping;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequestPipe httpRequestPipe = new CachedHttpRequest(new BufferedReaderAsHttpRequest(in));
            HttpRequest request = httpRequestPipe.request();
            HttpResponse response = new HttpResponse(out);

            if (!request.isRequestedSessionIdFromCookie()) {
                response.addHeader("Set-Cookie", HttpSessionStorage.SESSION_ID_NAME + "=" + UUID.randomUUID());
            }

            Controller controller = requestMapping.getController(request);
            logger.debug("Controller : {}", controller);
            controller.service(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
