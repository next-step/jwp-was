package webserver.response;

import webserver.Response;

import static webserver.HttpHeaders.TEXT_HTML_CHARSET_UTF_8;
import static webserver.response.HttpStatus.*;

public class ResponseFactory {

    public static Response ok(String responseBody) {
        return ok(responseBody.getBytes(), TEXT_HTML_CHARSET_UTF_8);
    }

    public static Response ok(byte[] responseBody) {
        return ok(responseBody, TEXT_HTML_CHARSET_UTF_8);
    }

    public static Response ok(byte[] responseBody, String contentType) {
        HttpResponse httpResponse = new HttpResponse(SUCCESS, responseBody);
        httpResponse.setContentType(contentType);
        httpResponse.setContentLength(responseBody.length);
        return httpResponse;
    }

    public static Response redirect(String location) {
        HttpResponse httpResponse = new HttpResponse(REDIRECT);
        httpResponse.setLocation(location);
        return httpResponse;
    }

    public static Response notFound() {
        return new HttpResponse(NOT_FOUND);
    }

    public static Response internalServerError() {
        return new HttpResponse(INTERNAL_SERVER_ERROR);
    }
}
