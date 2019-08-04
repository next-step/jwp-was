package webserver.http;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.resource.ResourceResolver;
import webserver.view.HandlebarViewResolver;
import webserver.view.HtmlViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class Dispatcher {
    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private static final String ACCEPT_SEPARATOR = ",";

    public void service(Request request, Response response) throws IOException, URISyntaxException {

        HttpMethod method = resolveMethod(request);

        if (HttpMethod.GET == method) {
            doGet(request, response);
        }

        if (HttpMethod.POST == method) {
            doPost(request, response);
        }

    }

    private void doPost(Request request, Response response) throws IOException {
        if (request.getPath().equals("/user/login")) {

            String userId = request.getBodyRequest("userId");
            String password = request.getBodyRequest("password");

            User user = DataBase.findUserById(userId);

            if (user == null || !user.getPassword().equals(password)) {
                logger.error("login failed {}", userId);
                response.writeRedirectHeader("/index.html", false);
            } else {
                response.writeRedirectHeader("/index.html", true);
            }
        }

        if (request.getPath().contains("/user/create")) {
            String userId = request.getBodyRequest("userId");
            String password = request.getBodyRequest("password");
            String name = request.getBodyRequest("name");
            String email = request.getBodyRequest("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            response.writeRedirectHeader("/index.html", false);
        }
    }

    private void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String accept = extractAccept(request);

        if (request.getPath().equals("/index.html")) {
            byte[] view = new HtmlViewResolver().render(request.getPath());
            response.writeHeader(accept, view.length);
            response.writeBody(view);
        }

        if (request.getPath().equals("/user/form.html")) {
            byte[] view = new HtmlViewResolver().render(request.getPath());
            response.writeHeader(accept, view.length);
            response.writeBody(view);
        }

        if (request.getPath().equals("/user/login.html")) {
            byte[] view = new HtmlViewResolver().render(request.getPath());
            response.writeHeader(accept, view.length);
            response.writeBody(view);
        }

        if (request.getPath().equals("/user/list.html")) {
            Boolean logined = Boolean.valueOf(request.getCookie("logined"));
            if (logined) {
                HandlebarViewResolver handlebarViewResolver = new HandlebarViewResolver();
                byte[] view = handlebarViewResolver.render("user/list");
                response.writeHeader(accept, view.length);
                response.writeBody(view);
            } else {
                response.writeRedirectHeader("/user/login.html", false);
            }
        }

        if (!accept.equals("text/html")) {
            byte[] resource = ResourceResolver.getResource(request.getPath());
            response.writeHeader(accept, resource.length);
            response.writeBody(resource);
        }

    }

    private static HttpMethod resolveMethod(Request request) {
        return HttpMethod.resolve(request.getMethod());
    }

    private String extractAccept(Request request) {
        String accept = request.getHeader("Accept");
        return accept.split(ACCEPT_SEPARATOR)[0];
    }
}
