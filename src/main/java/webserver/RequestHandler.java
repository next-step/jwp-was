package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.Cookie;
import webserver.http.request.Path;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            String path = request.getPath();
            Cookie cookie = request.getCookie();
            HttpResponse response = HttpResponse.from(out);
            switch (path) {
                case "/user/create":
                    User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
                    DataBase.addUser(user);

                    response.sendRedirect("/index.html");
                    break;
                case "/user/login":
                    User loginUser = DataBase.findUserById(request.getParameter("userId"));
                    boolean isLogin = !Objects.isNull(loginUser) && loginUser.checkPassword(request.getParameter("password"));
                    cookie.set("logined", String.valueOf(isLogin));

                    response.addHeader(Cookie.RESPONSE_COOKIE_HEADER, cookie.getResponseCookie());
                    response.sendRedirect(isLogin ? "/index.html" : "/user/login_failed.html");
                    break;
                case "/user/list":
                    boolean logined = Boolean.parseBoolean(cookie.get("logined"));
                    if (!logined) {
                        response.sendRedirect("/user/login.html");
                        break;
                    }

                    List<User> users = new ArrayList<>(DataBase.findAll());
                    Template template = handlebars.compile("user/list");
                    byte[] listPage = template.apply(users).getBytes();

                    response.responseBody(listPage);
                    break;
                default:
                    path = !Path.PATH_DELIMITER.equals(path) ? path : "/index.html";
                    String prefix = (path.contains(".html") || path.contains(".ico")) ? "templates" : "static";
                    byte[] responseBody = FileIoUtils.loadFileFromClasspath("./" + prefix + path);

                    response.responseBody(responseBody);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
