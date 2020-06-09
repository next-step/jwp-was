package webserver;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import model.User;
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

            HttpResponse response = new HttpResponse(out);
            HttpRequest request = new HttpRequest(br);

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
                    }
                }

                response.addHeaders("Set-Cookie", "logined=false; Path=/");
                response.redirect("/user/login_failed.html");
            } else {
                response.response(path);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
