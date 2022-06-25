package webserver.adapter.in.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.user.User;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class ListUserController extends AbstractController {

    private final UserProcessor userProcessor;

    public ListUserController(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.getHttpHeader().isLogined()) {
            httpResponse.responseRedirect("/user/login_failed.html", true, false);
            return;
        }

        Collection<User> users = userProcessor.getUsers();

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (context, options) -> (int) context + 1);

        Template template = handlebars.compile("user/list");
        String page = template.apply(Map.of("users", users));

        httpResponse.responseBody(page, true);
    }
}
