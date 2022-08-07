package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.controller.Controller;
import webserver.controller.StaticController;
import webserver.controller.TemplateController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class RequestMapping {
    private static final Set<Controller> CONTROLLERS = new HashSet<>();

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        CONTROLLERS.addAll(
                Set.of(
                        new StaticController(),
                        new TemplateController(),
                        new UserCreateController(),
                        new UserListController(new Handlebars(loader)),
                        new UserLoginController()
                )
        );
    }

    public static HttpResponse process(HttpRequest httpRequest) {
        return CONTROLLERS.stream()
                .filter(controller -> controller.isMatchRequest(httpRequest))
                .findAny()
                .map(controller -> requestProcess(httpRequest, controller))
                .orElseGet(HttpResponse::notFound);
    }

    private static HttpResponse requestProcess(HttpRequest httpRequest, Controller controller){
        try {
            return controller.process(httpRequest);
        } catch (IOException | URISyntaxException e) {
            return HttpResponse.notFound();
        }
    }
}
