package controller.user;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserListController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String URL = "/user/list";
    public static final String VIEW_PATH = "/user/list.html";

    public HttpResponse run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private HttpResponse doGet() {
        List<User> users = DataBase.findAll()
                .stream().collect(Collectors.toList());

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(URL);
            template.apply(users);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return HttpResponse.getView(VIEW_PATH);
    }
}
