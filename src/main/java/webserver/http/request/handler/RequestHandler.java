package webserver.http.request.handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ViewRenderer;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public interface RequestHandler {
    ViewRenderer handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
