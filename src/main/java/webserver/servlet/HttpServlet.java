package webserver.servlet;

import webserver.http.*;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseLine;

public abstract class HttpServlet implements Servlet {

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
        return new HttpResponse(responseLine, HttpHeaders.init(), ResponseBody.empty());
    }

    protected HttpResponse doGet(HttpRequest request) {
        HttpStatus httpStatus = validateNotAllowedMethod(request);
        ResponseLine responseLine = new ResponseLine(httpStatus);

        return new HttpResponse(responseLine, HttpHeaders.init(), ResponseBody.empty());
    }

    protected HttpResponse doPost(HttpRequest request) {
        HttpStatus httpStatus = validateNotAllowedMethod(request);
        ResponseLine responseLine = new ResponseLine(httpStatus);

        return new HttpResponse(responseLine, HttpHeaders.init(), ResponseBody.empty());
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
