package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.request.domain.request.*;
import webserver.response.HttpResponse;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    //private AbstractController controller = new AbstractController();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if (in != null) {
                httpRequest = new HttpRequest(in);
                httpResponse = new HttpResponse(out);

                matchResponse(httpRequest);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void matchResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        String method = httpRequest.getMethod();

        if (path.equals("/user/create")) {
            if (method.equals("GET")) {
                QueryString queryString = httpRequest.getQueryString();
                httpResponse.redirect(parseQueryString(path, queryString));
            } else {
                RequestBody requestBody = httpRequest.getBody();
                httpResponse.redirect(parseBody(path, requestBody));
            }
        } else if (path.equals("/user/login")) {
            if (isLogin()) {
                httpResponse.loginRedirect("/index.html", "logined=true");
            } else {
                httpResponse.loginRedirect("/user/login_failed.html", "logined=true");
            }
        } else if (path.equals("/user/list")) {
            String headerValue = httpRequest.getHeader("Cookie");
            if (headerValue == null) {
                httpResponse.redirect("/index.html");
            }
            else if (headerValue.equals("logined=true")) {
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlers = new Handlebars(loader);

                Template template = handlers.compile("user/list");

                Collection<User> users = DataBase.findAll();
                httpResponse.forward(template.apply(users));
            }
        } else {
            if (path.endsWith("html")) {
                httpResponse.forward(IOUtils.loadFileFromClasspath("./templates", path));
            }
            else if(path.endsWith("css")) {
                httpResponse.forwardCSS(IOUtils.loadFileFromClasspath("./static", path));
            }
        }
    }

    private boolean isLogin() {
        String userId = httpRequest.getBody().getParameter("userId");
        User user = DataBase.findUserById(userId);

        String pw = httpRequest.getBody().getParameter("password");

        return user.getPassword().equals(pw);
    }

    private String parseQueryString(String parsePath, QueryString queryString) {
        if (queryString != null) {
            String[] str = parsePath.split("/");
            if (str.length > 2) {
                if (str[1].equals("user")) {
                    userService(queryString.getDataPairs(), str);
                }
            }
        }

        return "/index.html";
    }

    private String parseBody(String parsePath, RequestBody requestBody) {
        String[] str = parsePath.split("/");
        if (str.length > 2) {
            if (str[1].equals("user")) {
                userService(requestBody.getDataPairs(), str);
            }
        }

        return "/index.html";
    }

    private void userService(Map<String, String> map, String[] str) {
        if (str[2].equals("create")) {
            User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
            DataBase.addUser(user);
        }
    }
}
