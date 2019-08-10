package webserver.controller;

import webserver.controller.exceptions.NotSupportedHttpMethodException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-11.
 */
public abstract class AbstractController implements Controller {
    @Override
    public final void action(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(httpRequest.getHttpMethod().isPost()) {
            doPost(httpRequest, httpResponse);
            return;
        }

        if(httpRequest.getHttpMethod().isGet()) {
            doGet(httpRequest, httpResponse);
            return;
        }

        throw new NotSupportedHttpMethodException(httpRequest.getHttpMethod());
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new NotSupportedHttpMethodException(httpRequest.getHttpMethod());

    }
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new NotSupportedHttpMethodException(httpRequest.getHttpMethod());
    }
}
