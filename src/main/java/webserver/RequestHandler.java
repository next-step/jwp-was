package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_PREFIX = "./templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequest.parse(new BufferedReader(new InputStreamReader(in)));
            logger.debug("Request {}", httpRequest.getRequestLine());

            HttpResponse httpResponse = getHttpResponse(httpRequest);
            httpResponse.response(dos, httpRequest);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    HttpResponse getHttpResponse(HttpRequest request) throws IOException, URISyntaxException {
        String requestPath = request.getUri().getPath();
        if (requestPath.endsWith(".html"))
            return new HttpResponse(FileIoUtils.loadFileFromClasspath(TEMPLATES_PREFIX + requestPath));

        byte[] body = Router.route(request).apply(request).orElse("").toString().getBytes();
        return new HttpResponse(body);
    }
}
