package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.StringUtils;
import webserver.http.*;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String INDEX_PAGE = "/index.html";
    public static final String TEMPLATES_PATH = "/templates";
    public static final String LOGIN_FAILED_PAGE = "/user/login_failed.html";

    public static final String COOKIE_LOGIN_NAME = "logined";

    private Socket connection;
    private final HandleBarsViewResolver viewResolver;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.viewResolver = new HandleBarsViewResolver(TEMPLATES_PATH, ".html");
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = null;

            if ("POST".equals(httpRequest.getMethod())
                    && "/user/create".equals(httpRequest.getPath())) {
                httpResponse = createUser(httpRequest);
            } else if ("POST".equals(httpRequest.getMethod())
                    && "/user/login".equals(httpRequest.getPath())) {
                httpResponse = login(httpRequest);
            } else if ("GET".equals(httpRequest.getMethod())
                    && "/user/list".equals(httpRequest.getPath())) {
                httpResponse = renderUserList(httpRequest);
            } else {
                if (httpRequest.getPath().lastIndexOf(".css") > -1) {
                    httpResponse = HttpResponse.ok("text/css", getStatic(httpRequest.getPath()));
                } else if (httpRequest.getPath().lastIndexOf(".js") > -1) {
                    httpResponse = HttpResponse.ok("text/javascript", getStatic(httpRequest.getPath()));
                } else {
                    httpResponse = HttpResponse.ok("text/html", getContent(httpRequest.getPath()));
                }
            }

            httpResponse.sendResponse(out);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private HttpResponse renderUserList(HttpRequest request) throws IOException {
        try {
            Cookie logined = request.getCookie(COOKIE_LOGIN_NAME);

            if (!Boolean.parseBoolean(logined.getValue())) {
                throw new AuthenticationException();
            }

            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());

            byte[] body = viewResolver.render(request.getPath(), model).getBytes();
            return HttpResponse.ok("text/html", body);
        } catch(RuntimeException e) {
            return HttpResponse.redirect(INDEX_PAGE);
        }
    }

    private HttpResponse login(HttpRequest request) throws URISyntaxException, IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        try {
            User foundUser = Optional.ofNullable(DataBase.findUserById(userId))
                    .orElseThrow(() -> new EntityNotFoundException(userId));

            if (!foundUser.equalToPassword(password)) {
                throw new AuthenticationException();
            };

            return HttpResponse.redirect(INDEX_PAGE, Arrays.asList(new Cookie(COOKIE_LOGIN_NAME, "true")));
        } catch (RuntimeException e) {
            return HttpResponse.ok("text/html", getContent(LOGIN_FAILED_PAGE), Arrays.asList(new Cookie(COOKIE_LOGIN_NAME, "false")));
        }
    }

    private HttpResponse createUser(HttpRequest request) {
        logger.debug("Create new user");

        User newUser = newUser(request);

        DataBase.addUser(newUser);

        return HttpResponse.redirect(INDEX_PAGE);
    }

    private User newUser(HttpRequest request) {
        try {
            return new User(
                    request.getParameter("userId"),
                    request.getParameter("password"),
                    StringUtils.unescape(request.getParameter("name")),
                    StringUtils.unescape(request.getParameter("email"))
            );
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private byte[] getContent(String path) throws IOException, URISyntaxException {
        Path templatePath = Paths.get(".", TEMPLATES_PATH, path);
        try {
            return FileIoUtils.loadFileFromClasspath(templatePath.toString());
        } catch (NullPointerException e) {
            throw new FileNotFoundException(path);
        }
    }


    private byte[] getStatic(String path) throws IOException, URISyntaxException {
        Path templatePath = Paths.get(".", "static", path);
        try {
            return FileIoUtils.loadFileFromClasspath(templatePath.toString());
        } catch (NullPointerException e) {
            throw new FileNotFoundException(path);
        }
    }

}
