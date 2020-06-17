package Controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        if (httpRequest.isGetMethod()) {
            return doGet(httpRequest);
        }

        if (httpRequest.isPostMethod()) {
            return doPost(httpRequest);
        }

        return HttpResponse.makeResponseWithHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
    }

    public HttpResponse doGet(HttpRequest httpRequest) {
        // do nothing
        return null;
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        // do nothing
        return null;
    }
}
