package service;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.HttpStatus;
import types.MediaType;
import utils.HandlerAdapter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);
    private static final List<String> RESOURCE_FILE_EXTENSIONS = List.of(".css", ".js", ".ico", "ttf", "woff", "png");
    private static final String HTML_EXTENSION = ".html";
    private static final String BODY_SEPARATOR = "";

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
        if (isRequestForFileResource(requestLine)) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_CSS);

            UrlPath urlPath = requestLine.getUrlPath();
            HttpResponseMessage httpResponseMessage = new HttpResponseMessage(HttpStatus.OK, httpHeaders);
            httpResponseMessage.setFileBody(urlPath, false);
            return httpResponseMessage;
        }

        if (requestLine.getUrlPath().getPath().contains(HTML_EXTENSION)) {
            UrlPath urlPath = requestLine.getUrlPath();
            HttpResponseMessage httpResponseMessage = new HttpResponseMessage(HttpStatus.OK, null);
            httpResponseMessage.setFileBody(urlPath, true);
            return httpResponseMessage;
        }

        logger.info("Request data >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n" + httpRequestMessage.toStringHttpMessage());
        AuthService.getInstance().setUserCredential(httpRequestMessage.getRequestHeaders().getRequestHeaders());
        HttpResponseMessage httpResponseMessage = HandlerAdapter.getInstance().invoke(httpRequestMessage);

        AuthService.getInstance().removeUserCredential();

        return httpResponseMessage;
    }

    public static byte[] bodyToBytes(Object body) throws IOException {
        if (body == null) {
            return null;
        }

        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(body);
            return boas.toByteArray();
        }
    }

    private static boolean isRequestForFileResource(RequestLine requestLine) {
        return RESOURCE_FILE_EXTENSIONS.stream().anyMatch(requestLine.getUrlPath().getPath()::contains);
    }

}




















