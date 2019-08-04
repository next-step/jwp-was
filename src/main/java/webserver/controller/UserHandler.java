package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserHandler implements RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        RequestBody requestBody = request.getRequestBody();

        if ("/user/create".equals(request.getRequestUriPath())) {
            User user = new User(requestBody.getValue("userId"), requestBody.getValue("password"),
                    requestBody.getValue("name"), requestBody.getValue("email"));
            DataBase.addUser(user);
            logger.debug("User : {}", user);

            response.response302Header("/index.html", false);
        }

        if("/user/list".equals(request.getRequestUriPath()) || "/user/list.html".equals(request.getRequestUriPath())) {
            if ("logined=true".equals(request.getHeader("Cookie"))) {
                byte[] body = loadTemplate();

                response.response200Header(body.length, "text/html;");
                response.responseBody(body);

            } else {
                response.response302Header("/user/login.html", false);
            }
        }
    }

    private byte[] loadTemplate() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        loader.setCharset(Charsets.UTF_8);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        String usersPage = template.apply(data);

        return usersPage.getBytes();
    }
}
