package webserver.adapter.in;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.user.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserController {

    private final static String USER_LOGIN_PATH = "/user/login";
    private final static String USER_CREATE_PATH = "/user/create";
    private final static String USER_LIST_PATH = "/user/list.html";


    private final UserProcessor userProcessor;
    private static final Set<String> handlers = new HashSet<>();

    public UserController(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
        handlers.add(USER_LOGIN_PATH);
        handlers.add(USER_CREATE_PATH);
        handlers.add(USER_LIST_PATH);
    }

    public boolean isSupport(String path) {
        return handlers.stream()
                .anyMatch(handler -> handler.equals(path));
    }

    public void handle(HttpRequest request, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = request.getUri().getPath();

        if (USER_LOGIN_PATH.equals(path)) {
            boolean validUser = userProcessor.isValidUser(request.getRequestBody().get("userId"), request.getRequestBody().get("password"));

            if (validUser) {
                httpResponse.responseRedirect("/index.html", true, true);
                return;
            }

            httpResponse.responseRedirect("/user/login_failed.html", true, false);
            return;
        }

        if (USER_CREATE_PATH.equals(path)) {
            userProcessor.createUser(request.getRequestBody().get("userId"), request.getRequestBody().get("password"), request.getRequestBody().get("name"), request.getRequestBody().get("email"));
            httpResponse.responseRedirect("/index.html", false, false);
            return;
        }

        if (USER_LIST_PATH.equals(path)) {
            if (!request.getHttpHeader().isLogined()) {
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
}
