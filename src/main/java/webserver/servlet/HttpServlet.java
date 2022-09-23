package webserver.servlet;

import webserver.http.*;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

public abstract class HttpServlet implements Servlet {

    private static final String HTTP_BODY_MESSAGE = "message=%s";

    @Override
    public String getRequestPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpResponse service(HttpRequest request) {
        Method method = request.getRequestLine().getMethod();

        if (method == Method.GET) {
            return doGet(request);
        }
        if (method == Method.POST) {
            return doPost(request);
        }

        ResponseLine responseLine = new ResponseLine(HttpStatus.NOT_IMPLEMENTED);
        String message = "http.method_not_implemented";
        String responseBody = String.format(HTTP_BODY_MESSAGE, message);
        return new HttpResponse(responseLine, HttpHeaders.init(), HttpBody.from(responseBody));
    }

    protected HttpResponse doGet(HttpRequest request) {
        HttpStatus httpStatus = validateNotAllowedMethod(request);
        ResponseLine responseLine = new ResponseLine(httpStatus);

        String message = "http.method_get_not_supported";
        String responseBody = String.format(HTTP_BODY_MESSAGE, message);

        return new HttpResponse(responseLine, HttpHeaders.init(), HttpBody.from(responseBody));
    }

    protected HttpResponse doPost(HttpRequest request) {
        HttpStatus httpStatus = validateNotAllowedMethod(request);
        ResponseLine responseLine = new ResponseLine(httpStatus);

        String message = "http.method_post_not_supported";
        String responseBody = String.format(HTTP_BODY_MESSAGE, message);

        return new HttpResponse(responseLine, HttpHeaders.init(), HttpBody.from(responseBody));
    }

    private HttpStatus validateNotAllowedMethod(HttpRequest request) {
        Type protocolType = request.getRequestLine().getProtocolType();
        Version protocolVersion = request.getRequestLine().getProtocolVersion();

        if (protocolType.name().length() == 0 || protocolVersion.getLabel().equals("0.9") || protocolVersion.getLabel().equals("1.0")) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.METHOD_NOT_ALLOWED;
        }
    }
}
