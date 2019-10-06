package webserver.http.response;

import utils.FileIoUtils;
import webserver.http.mapping.RequestMapping;
import webserver.http.mapping.ResourceMapping;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponseResolver {

    public static HttpResponse forward(String contentType, Object resBody) {
        byte[] responseBody = getResponseBody(resBody);
        return new HttpResponse().forward(contentType, responseBody);
    }

    public static HttpResponse redirect(String requestUri) {
        byte[] responseBody = getResponseBody(requestUri);
        return new HttpResponse().found(requestUri, responseBody);
    }

    public static HttpResponse redirect(String requestUri, String... cookies) {
        byte[] responseBody = getResponseBody(requestUri);
        return new HttpResponse().found(requestUri, responseBody, cookies);
    }

    public static HttpResponse resource(String requestUri) {
        byte[] responseBody = getResourceBody(requestUri);
        String contentType = ResourceMapping.getContentType(requestUri);
        return new HttpResponse().forward(contentType, responseBody);
    }

    private static byte[] getResponseBody(Object resBody) {
        if (resBody instanceof byte[]) {
            return (byte[])resBody;
        }

        String filePath = RequestMapping.getFilePath((String) resBody);

        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] getResourceBody(String requestUri) {
        try {
            String filePath = ResourceMapping.getFilePath(requestUri);
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
