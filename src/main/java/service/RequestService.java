package service;

import model.ClientResponse;
import model.HttpMessage;
import model.RequestLine;
import model.UrlPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private static final List<String> RESOURCE_FILE_EXTENSIONS = List.of(".css", ".js", ".ico");
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

    public static ClientResponse getClientResponse(HttpMessage httpMessage) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {

        AuthService.getInstance().setUserCredential(httpMessage.getRequestHeaders().getRequestHeaders());

        RequestLine requestLine = httpMessage.getRequestLine();
        if (isRequestForFileResource(requestLine)) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.put(HttpHeaders.CONTENT_TYPE, List.of("text/css"));

            UrlPath urlPath = requestLine.getUrlPath();
            ClientResponse clientResponse = new ClientResponse(HttpStatus.OK, httpHeaders);
            clientResponse.setFileBody(urlPath, false);
            AuthService.getInstance().removeUserCredential();
            return clientResponse;
        }

        logger.info("Request data >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n" + httpMessage.toStringHttpMessage());
        if (requestLine.getUrlPath().getPath().contains(HTML_EXTENSION)) {
            UrlPath urlPath = requestLine.getUrlPath();
            ClientResponse clientResponse = new ClientResponse(HttpStatus.OK, null);
            clientResponse.setFileBody(urlPath, true);
            AuthService.getInstance().removeUserCredential();
            return clientResponse;
        }

        ClientResponse clientResponse = HandlerAdapter.getInstance().invoke(httpMessage);
        if (clientResponse != null && clientResponse.getBody() != null) {
            clientResponse.convertBodyToBytes();
        }

        AuthService.getInstance().removeUserCredential();

        return clientResponse;
    }

    public static byte[] bodyToBytes(Object result) throws IOException {
        if (result == null) {
            return null;
        }

        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(result);
            return boas.toByteArray();
        }
    }

    private static boolean isRequestForFileResource(RequestLine requestLine) {
        return RESOURCE_FILE_EXTENSIONS.stream().anyMatch(requestLine.getUrlPath().getPath()::contains);
    }

}



















