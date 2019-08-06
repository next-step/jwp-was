package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.request.Request;
import webserver.http.response.Response;

public abstract class AbstractController implements Controller {

    protected void doGet(final Request request,
                         final Response response) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected void doPost(final Request request,
                          final Response response) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void service(final Request request,
                        final Response response) throws Exception {
        if (request.matchMethod(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.matchMethod(HttpMethod.POST)) {
            doPost(request, response);
        }
    }
}
