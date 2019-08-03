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
            response(dos, httpRequest, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    HttpResponse getHttpResponse(HttpRequest request) throws IOException, URISyntaxException {
        String requestPath = request.getUri().getPath();

        if (requestPath.endsWith(".html"))
            return new HttpResponse(FileIoUtils.loadFileFromClasspath(TEMPLATES_PREFIX + requestPath));

        HttpResponse response = new HttpResponse();
        response.setBody(Router.route(request, response).orElse("").toString().getBytes());
        return response;
    }

    private void response(DataOutputStream dos, HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] body = httpResponse.getBody();
        String bodyString = new String(httpResponse.getBody());

        if (bodyString.startsWith("redirect:")) {
            String redirectPath = String.format("http://%s%s", httpRequest.getHeaders().get("Host"), bodyString.substring(bodyString.indexOf(":") + 1));
            httpResponse.response302Header(dos, redirectPath);
            return;
        }

        httpResponse.response200Header(dos);
        httpResponse.responseBody(dos, body);
    }
}
