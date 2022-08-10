package service;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.HttpStatus;
import types.MediaType;
import utils.HandlerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);
    private static final String BODY_SEPARATOR = "";
    private static final List<String> SESSION_CHECK_EXCLUDE_URLS = List.of("/");

    public static List<String> getHttpMessageData(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        List<String> data = new ArrayList<>();
        while (!line.equals(BODY_SEPARATOR)) {
            data.add(line);
            line = bufferedReader.readLine();
        }
        return data;
    }

    public static HttpResponseMessage getClientResponse(HttpRequestMessage httpRequestMessage) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {

        RequestLine requestLine = httpRequestMessage.getRequestLine();
        if ((!SESSION_CHECK_EXCLUDE_URLS.contains(requestLine.getUrlPath().getPath())) && (!hasSession(httpRequestMessage))) {
            return redirectHome();
        }

        if (requestLine.isRequestForFileResource(requestLine)) {
            return getFileResourceResponse(requestLine);
        }

        if (requestLine.isRequestForHtml()) {
            return getHtmlResponse(requestLine);
        }

        logger.info("Request data >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n" + httpRequestMessage.toStringHttpMessage());
        AuthService.getInstance().setUserCredential(httpRequestMessage.getRequestHeaders());

        HttpResponseMessage httpResponseMessage = HandlerAdapter.getInstance().invoke(httpRequestMessage);

        AuthService.getInstance().removeUserCredential();

        return httpResponseMessage;
    }

    private static boolean hasSession(HttpRequestMessage httpRequestMessage) {
        HttpHeaders requestHeaders = httpRequestMessage.getRequestHeaders();
        String sessionId = requestHeaders.getSessionId();

        return (sessionId != null);
    }

    private static HttpResponseMessage redirectHome() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        httpHeaders.setLocation(URI.create("http://localhost:8080/").toString());
        return new HttpResponseMessage(HttpStatus.FOUND, httpHeaders);
    }

    private static HttpResponseMessage getHtmlResponse(RequestLine requestLine) throws IOException, URISyntaxException {
        UrlPath urlPath = requestLine.getUrlPath();
        HttpResponseMessage httpResponseMessage = new HttpResponseMessage(HttpStatus.OK, null);
        httpResponseMessage.setFileBody(urlPath, true);
        return httpResponseMessage;
    }

    private static HttpResponseMessage getFileResourceResponse(RequestLine requestLine) throws IOException, URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_CSS);

        UrlPath urlPath = requestLine.getUrlPath();
        HttpResponseMessage httpResponseMessage = new HttpResponseMessage(HttpStatus.OK, httpHeaders);
        httpResponseMessage.setFileBody(urlPath, false);

        return httpResponseMessage;
    }

}




















