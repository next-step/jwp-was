package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.*;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String REDIRECT_START_WITH = "redirect:";
    private static final String HEADER_HOST_KEY = "Host";
    private static final String REDIRECT_URL_FORMAT = "http://%s%s";

    private Socket connection;
    private WebServerRouter router;

    public RequestHandler(Socket connectionSocket, WebServerRouter router) {
        this.connection = connectionSocket;
        this.router = router;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            RequestStream requestStream = new RequestStream(in);
            HttpRequest httpRequest = HttpRequest.parse(requestStream);
            logger.debug("Request {}", httpRequest.getRequestLine());

            writeResponse(httpRequest, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    void writeResponse(HttpRequest httpRequest, DataOutputStream dos) {
        String requestPath = httpRequest.getUri().getPath();
        HttpResponse httpResponse = FileResponse.getFileResponse(requestPath)
                .orElse(getViewMappingResponse(httpRequest));

        httpResponse.responseByStatus(dos);
    }

    private HttpResponse getViewMappingResponse(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        ModelAndView modelAndView = httpResponse.getModelAndView();
        String viewName = getViewName(httpRequest, httpResponse);
        modelAndView.setView(viewName);
        if (viewName.startsWith(REDIRECT_START_WITH)) {
            return getRedirectHttpResponse(httpRequest, httpResponse, viewName);
        }

        setResponseBody(httpResponse);
        return httpResponse;
    }

    private void setResponseBody(HttpResponse httpResponse) {
        ModelAndView modelAndView = httpResponse.getModelAndView();
        try {
            httpResponse.setBody(ViewResolver.mapping(modelAndView).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse getRedirectHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse, String viewName) {
        String redirectPath = viewName.replace(REDIRECT_START_WITH, StringUtils.EMPTY);
        String redirectUrl = String.format(REDIRECT_URL_FORMAT, httpRequest.getHeaderValue(HEADER_HOST_KEY), redirectPath);
        httpResponse.setRedirectPath(redirectUrl);
        httpResponse.setHttpStatus(HttpStatus.REDIRECT);
        return httpResponse;
    }

    private String getViewName(HttpRequest httpRequest, HttpResponse httpResponse) {
        return router.route(httpRequest, httpResponse).orElse(StringUtils.EMPTY);
    }
}
