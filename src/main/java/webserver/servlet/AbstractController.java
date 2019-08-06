package webserver.servlet;

import exception.HttpException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.RequestLine;

import static java.util.Optional.of;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        switch (of(httpRequest.getRequestLine())
                .map(RequestLine::getMethod)
                .orElseThrow(HttpException::new)) {
            case GET:
                doGet(httpRequest, httpResponse);
                break;
            case POST:
                doPost(httpRequest, httpResponse);
                break;
            default:
                throw new HttpException("unsupported http method");
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

}
