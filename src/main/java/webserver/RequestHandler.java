package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;
import webserver.http.template.handlebars.HandlebarsTemplateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

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
                final boolean logined = Boolean.parseBoolean(httpRequest.getCookie("logined").getValue());

                if (!logined) {
                    httpResponse.responseRedirect("/index.html");
                    return;
                }

                final Collection<User> users = DataBase.findAll();
                final Map<String, Object> params = Map.of("users", users);

                final HandlebarsTemplateLoader handlebarsTemplateLoader = new HandlebarsTemplateLoader();
                final String page = handlebarsTemplateLoader.load("user/list", params);

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
