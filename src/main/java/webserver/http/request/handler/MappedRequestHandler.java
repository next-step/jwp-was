package webserver.http.request.handler;

import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.controller.Controller;
import webserver.http.request.controller.ControllerType;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.TemplateResourceViewRenderer;
import webserver.http.response.view.ViewRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class MappedRequestHandler implements RequestHandler {

    private static final String PREFIX_REDIRECT = "redirect::";
    private final String prefix;
    private static Map<String, Controller> mappingControllerCache = new HashMap<>();
    static {
        for (ControllerType type : ControllerType.values()) {
            mappingControllerCache.put(type.getUrl(), type.getController());
        }
    }

    public MappedRequestHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public ViewRenderer handle(HttpRequest httpRequest, HttpResponse httpResponse) {

        HttpMethod method = httpRequest.getHttpMethod();
        String url = httpRequest.getPath();
        String viewName = "";

        Controller controller = mappingControllerCache.get(url);

        if (HttpMethod.GET == method) {
            viewName = controller.getProcess(httpRequest, httpResponse);
        } else if (HttpMethod.POST == method) {
            viewName = controller.postProcess(httpRequest, httpResponse);
        }

        if (viewName.contains(PREFIX_REDIRECT)) {
            String redirectUrl = viewName.substring(PREFIX_REDIRECT.length());
            httpResponse.sendRedirect(redirectUrl);
        }

        return new TemplateResourceViewRenderer(httpResponse, httpRequest.getPath());
    }
}
