package webserver.http.request.handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class StaticResourceRequestHandler implements RequestHandler {

    private final String prefix;

    public StaticResourceRequestHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(ViewType.RESOURCE.getPrefix() + prefix + httpRequest.getPath());
    }
}
