package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.io.*;
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
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequest.parse(new BufferedReader(new InputStreamReader(in)));
            logger.debug("Request {}", httpRequest.getRequestLine());

            HttpResponse httpResponse = getResponse(httpRequest);
            httpResponse.responseByStatus(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    HttpResponse getResponse(HttpRequest httpRequest) {
        String requestPath = httpRequest.getUri().getPath();
        HttpResponse httpResponse = new HttpResponse();

        return FileResponseEnum.getFileResponse(requestPath)
                .orElse(getViewMappingResponse(httpRequest, httpResponse));
    }

    private HttpResponse getViewMappingResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
        String viewName = getViewName(httpRequest, httpResponse);
        if (viewName.startsWith("redirect:")) {
            return getRedirectHttpResponse(httpRequest, httpResponse, viewName);
        }

        try {
            httpResponse.setBody(ViewResolver.mapping(httpRequest, httpResponse).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return httpResponse;
    }

    private HttpResponse getRedirectHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse, String viewName) {
        String redirectPath = String.format("http://%s%s", httpRequest.getHeaders().get("Host"), viewName.substring(viewName.indexOf(":") + 1));
        httpResponse.setRedirectPath(redirectPath);
        httpResponse.setHttpStatus(HttpStatus.REDIRECT);
        return httpResponse;
    }

    private String getViewName(HttpRequest httpRequest, HttpResponse httpResponse) {
        return Router.route(httpRequest, httpResponse).orElse("");
    }
}
