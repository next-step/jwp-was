package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import model.User;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpRequest request = new HttpRequest(br);
            HttpResponse response = new HttpResponse(out);

            String path = request.getPath();

            logger.debug("Content-Length: {}", request.getHeader("Content-Length"));

            if ("/user/create".equals(path)) {
                User user = new User(request.getParameter("userId"), request.getParameter("password"),
                    request.getParameter("name"), request.getParameter("email"));
                logger.debug("User : {}", user);
                DataBase.addUser(user);

                response.redirect("/index.html");
            } else if ("/user/login".equals(path)) {
                User user = DataBase.findUserById(request.getParameter("userId"));
                if (user != null) {
                    if (user.getPassword().equals(request.getParameter("password"))) {
                        response.addHeaders("Set-Cookie", "logined=true; Path=/");
                        response.redirect("/index.html");
                    } else {
                        response.addHeaders("Set-Cookie", "logined=false; Path=/");
                        response.redirect("/user/login_failed.html");
                    }
                } else {
                    response.addHeaders("Set-Cookie", "logined=false; Path=/");
                    response.redirect("/user/login_failed.html");
                }
            } else if ("/user/list".equals(path)) {
                if (request.getHeader("Cookie").contains("logined=true")) {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);
                    Template template = handlebars.compile(path);
                    Users users = new Users(new ArrayList<>(DataBase.findAll()));
                    String userList = template.apply(users);
                    logger.debug("template result : " + userList);
                    response.responseBody(userList);
                } else {
                    response.redirect("/user/login.html");
                }
            } else {
                response.response(path);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
