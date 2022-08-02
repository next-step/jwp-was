package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatusCode;
import model.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class DispatchController {
    private static Set<Controller> controllers;

    static {
        controllers = Set.of(
                new CreateUserController(),
                new ResourceController(),
                new LoginController(),
                new UserListController(handlebars())
        );
    }

    private static Handlebars handlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return new Handlebars(loader);
    }

    public HttpResponse handleRequest(HttpRequest request) {
        HttpResponse httpResponse = controllers.stream()
                .filter(controller -> controller.matchHttpMethodAndPath(request))
                .findAny()
                .map(controller -> execute(controller, request))
                .orElseGet(() -> HttpResponse.notFound());
        return httpResponse;

    }

    private HttpResponse execute(Controller controller, HttpRequest request) {
        HttpResponse response;
        try {
            response = controller.execute(request);
        } catch (IOException | URISyntaxException e) {
            return HttpResponse.of(HttpStatusCode.INTERNAL_SERVER_ERROR, ResponseHeader.empty());
        }

        return response;
    }
}
