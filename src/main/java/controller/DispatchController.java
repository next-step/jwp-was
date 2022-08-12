package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class DispatchController {
    private static final Set<Controller> controllers;

    static {
        controllers = Set.of(
                new CreateUserController(),
                new TemplateController(),
                new LoginController(),
                new UserListController(handlebars()),
                new StaticController()
        );
    }

    private static Handlebars handlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return new Handlebars(loader);
    }

    public HttpResponse handleRequest(HttpRequest request, DataOutputStream dos) {
        return controllers.stream()
                .filter(controller -> controller.match(request))
                .findAny()
                .map(controller -> execute(controller, request, dos))
                .orElseGet(HttpResponse::notFound);
    }

    private HttpResponse execute(Controller controller, HttpRequest request, DataOutputStream dos) {
        HttpResponse response = HttpResponse.of(dos);
        try {
            response = controller.execute(request, response);
        } catch (IOException | URISyntaxException e) {
            return HttpResponse.error();
        }
        return response;
    }
}
