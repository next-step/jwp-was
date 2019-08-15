package webserver.http.request.handler;

import webserver.WebConfig;
import webserver.http.request.HttpRequest;
import webserver.http.request.controller.Controller;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

import java.util.Optional;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class RequestMappingHandler implements RequestHandler {

    private final String prefix;

    public RequestMappingHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {

        String path = httpRequest.getPath();
        ModelAndView modelAndView = new ModelAndView(ViewType.TEMPLATE.getPrefix() + "/error");

        Controller controller = WebConfig.getController(path);
        if (controller == null) {
            return modelAndView;
        }

        // check login
        if (controller.isAllowAll() || isLogin(httpRequest)) {
            modelAndView = controller.process(httpRequest, httpResponse);
        }

        return modelAndView;
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return Optional.ofNullable(httpRequest.getHttpSession())
                .map(httpSession -> httpSession.getAttribute("user")).isPresent();
    }
}
