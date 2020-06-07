package http.controller;

import http.HttpMethod;
import http.HttpStatus;
import http.exception.HttpException;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            if (HttpMethod.GET.equals(httpRequest.getMethod())) {
                doGet(httpRequest, httpResponse);
                return;
            }

            if (HttpMethod.POST.equals(httpRequest.getMethod())) {
                doPost(httpRequest, httpResponse);
                return;
            }
        }
        catch (HttpException e) {
            doException(httpRequest, httpResponse);
        }
        catch (Exception e) {
            doException(httpRequest, new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    protected void doException(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
