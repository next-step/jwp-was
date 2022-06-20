package webserver.adapter.in;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.HttpHeader;
import webserver.domain.http.RequestBody;
import webserver.domain.http.RequestLine;
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

    public void handle(RequestLine requestLine, HttpHeader header, RequestBody body, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = requestLine.getUri().getPath();

        if (USER_LOGIN_PATH.equals(path)) {
            boolean validUser = userProcessor.isValidUser(body.get("userId"), body.get("password"));

            if (validUser) {
                httpResponse.responseRedirectSetCookie("/index.html", true);
                return;
            }

            httpResponse.responseRedirectSetCookie("/user/login_failed.html", false);
            return;
        }

        if (USER_CREATE_PATH.equals(path)) {
            userProcessor.createUser(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
            httpResponse.responseRedirect("/index.html");
            return;
        }

        if (USER_LIST_PATH.equals(path)) {
            if (!header.isLogined()) {
                httpResponse.responseRedirectSetCookie("/user/login_failed.html", false);
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

            httpResponse.responseOkBody(page, true);
        }
    }
}
