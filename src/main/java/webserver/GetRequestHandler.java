package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GetRequestHandler implements MethodRequestHandler {

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        RequestUri uri = request.getRequestLine().getUri();

        if(uri.getPath().contains("/user/list")) {
            if ("logined=true".equals(request.getHeader("Cookie"))) {
                byte[] body = loadTemplate();

                response.response200Header(body.length, "text/html;");
                response.responseBody(body);

            } else {
                response.response302Header("/user/login.html", false);
            }
        } else {
            String resourcePath = ResourceHandler.getResourcePath(uri);
            byte [] body = ResourceHandler.loadResource(resourcePath);

            response.response200Header(body.length,ResourceHandler.resourceContentType(uri));
            response.responseBody(body);
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
