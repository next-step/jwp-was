package webserver.http.request.handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.TemplateResourceViewRenderer;
import webserver.http.response.view.ViewRenderer;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class TemplateRequestHandler implements RequestHandler {

    @Override
    public ViewRenderer handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new TemplateResourceViewRenderer(httpResponse, httpRequest.getPath());
    }
}
