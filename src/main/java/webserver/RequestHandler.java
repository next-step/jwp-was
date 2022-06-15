package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.Cookie;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = new HttpRequest(in);
            final HttpResponse httpResponse = new HttpResponse(out);

            if (this.isSignUpRequest(httpRequest)) {
                this.handleSignUpRequest(httpRequest);
                httpResponse.responseRedirect("/index.html");
                return;
            }

            if (this.isLoginRequest(httpRequest)) {
                final boolean loginSuccess = this.handleLoginRequest(httpRequest);

                if (loginSuccess) {
                    httpResponse.setCookie(new Cookie("logined", "true"));
                    httpResponse.responseRedirect("/index.html");
                    return;
                }

                httpResponse.setCookie(new Cookie("logined", "false"));
                httpResponse.responseRedirect("/user/login_failed.html");
                return;
            }

            if (httpRequest.getPath().equals("/user/list")) {
                final boolean logined = Boolean.parseBoolean(httpRequest.getCookie("logined"));

                if (!logined) {
                    httpResponse.responseRedirect("/index.html");
                    return;
                }

                // TODO refactor
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);
                handlebars.registerHelper("inc", (context, options) -> (int) context + 1);

                final Collection<User> users = DataBase.findAll();
                final Map<String, Object> params = Map.of("users", users);

                Template template = handlebars.compile("user/list");
                String page = template.apply(params);

                httpResponse.setContentType("text/html;charset=utf-8");
                httpResponse.setBody(page);
                httpResponse.responseOK();
                return;
            }

            httpResponse.setBodyContentPath(httpRequest.getPath());
            httpResponse.responseOK();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isSignUpRequest(final HttpRequest httpRequest) {
        return httpRequest.getMethod().equals(HttpMethod.POST) && httpRequest.getPath().equals("/user/create");
    }

    private void handleSignUpRequest(final HttpRequest httpRequest) {
        final RequestBody requestBody = httpRequest.getBody();

        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        final String name = requestBody.get("name");
        final String email = requestBody.get("email");

        DataBase.addUser(new User(userId, password, name, email));
    }

    private boolean isLoginRequest(final HttpRequest httpRequest) {
        return httpRequest.getMethod().equals(HttpMethod.POST) && httpRequest.getPath().equals("/user/login");
    }

    private boolean handleLoginRequest(final HttpRequest httpRequest) {
        final RequestBody requestBody = httpRequest.getBody();

        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");

        final Optional<User> user = DataBase.findUserById(userId);
        return user.map(it -> it.isPasswordMatched(password))
                .orElse(false);
    }
}
