package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.domain.Cookie;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.StatusCode;
import webserver.http.view.request.RequestReader;
import webserver.http.domain.exception.NullRequestException;
import webserver.http.view.response.ResponseWriter;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestReader requestReader;
    private final ResponseWriter responseWriter;

    public RequestHandler(Socket connectionSocket, RequestReader requestReader, ResponseWriter responseWriter) {
        this.connection = connectionSocket;
        this.requestReader = requestReader;
        this.responseWriter = responseWriter;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())
        ) {
            Request request = requestReader.read(bufferedReader);
            logger.info("[request] = {}", request);

            if (request.hasMethod(Method.GET) && request.hasPath("/")) {
                String resource = "/index.html";
                byte[] bytes = FileIoUtils.loadFileFromClasspath("./static" + resource);
                Response response = Response.ok();
                response.addHeader("Content-Type", findContentType(resource));
                response.addBody(bytes);
                responseWriter.write(dos, response);
                return;
            }


            if (request.hasMethod(Method.GET) && isResource(request)) {
                String resource = request.getPath();
                byte[] bytes = FileIoUtils.loadFileFromClasspath("./static" + resource);
                Response response = Response.ok();
                response.addHeader("Content-Type", findContentType(resource));
                response.addBody(bytes);
                responseWriter.write(dos, response);
                return;
            }

            if (request.hasMethod(Method.POST) && request.hasPath("/user/create")) {
                request.decodeCharacter(Charsets.UTF_8);
                String userId = request.getParameter("userId");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                User newUser = new User(userId, password, name, email);
                DataBase.addUser(newUser);
                logger.info("[Add User = {}]", newUser);
                Response response = Response.sendRedirect("/index.html");
                responseWriter.write(dos, response);
                return;
            }

            if (request.hasMethod(Method.POST) && request.hasPath("/user/login")) {
                request.decodeCharacter(Charsets.UTF_8);
                String userId = request.getParameter("userId");
                String password = request.getParameter("password");

                try {
                    User findUser = Optional.ofNullable(DataBase.findUserById(userId))
                            .filter(user -> user.hasPassword(password))
                            .orElseThrow(() -> new RuntimeException("로그인에 실패했습니다."));

                    logger.info("[login User] = {}", findUser);
                    Response response = Response.sendRedirect("/index.html");
                    response.addCookie(new Cookie("logined", "true"));
                    responseWriter.write(dos, response);
                    return;

                } catch (RuntimeException ex) {
                    logger.info("[login Fail]");
                    Response response = Response.sendRedirect("/user/login_failed.html");
                    response.addCookie(new Cookie("logined", "false"));
                    responseWriter.write(dos, response);
                    return;
                }
            }

            if (request.hasMethod(Method.GET) && request.hasPath("/user/list")) {
                boolean isLogin = Optional.ofNullable(request.getHeader("Cookie"))
                        .map(value -> value.contains("logined=true"))
                        .orElse(false);

                if (isLogin) {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile("user/list");

                    Collection<User> users = DataBase.findAll();
                    logger.info("[Users] = {}", users);
                    String page = template.apply(Map.of("users", users));
                    byte[] body = page.getBytes();
                    Response response = Response.ok();
                    response.addBody(body);
                    responseWriter.write(dos, response);
                    return;
                }
                Response response = Response.sendRedirect("/user/login.html");
                responseWriter.write(dos, response);
                return;
            }

            Response resourceNotFound = Response.from(StatusCode.NOT_FOUND);
            resourceNotFound.addHeader("Content-Type", "text/html");
            resourceNotFound.addBody("<h1><meta charset=\"UTF-8\">요청하신 리소스를 찾지 못했습니다. ;(</h1>");
            responseWriter.write(dos, resourceNotFound);
        } catch (NullRequestException e) {
            logger.warn(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String findContentType(String resource) {
        int startIndexOfFileExtension = resource.lastIndexOf(".") + 1;
        String fileExtension = resource.substring(startIndexOfFileExtension);

        switch (fileExtension) {
            case "css": return "text/css";
            case "js": return "text/javascript";
            case "html": return "text/html";
            case "png": return "image/png";
            case "ico": return "image/vnd.microsoft.icon";
            case "eot": return "font/eot";
            case "svg": return "font/svg";
            case "ttf": return "font/ttf";
            case "woff": return "font/woff";
            case "woff2": return "font/woff2";
            default: throw new RuntimeException("지원하지 않는 리소스");
        }
    }

    private boolean isResource(Request request) {
        URL resource = FileIoUtils.class.getClassLoader().getResource("./static" + request.getPath());
        if (Objects.isNull(resource)) {
            return false;
        }
        File file = getFile(resource);
        return file.isFile();
    }

    private File getFile(@Nonnull URL resource) {
        try {
            return Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
