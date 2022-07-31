package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Contents;
import webserver.http.Headers;
import webserver.http.HttpBody;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Path;
import webserver.template.UserList;
import webserver.user.UserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

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
            HttpRequest httpRequest = new HttpRequest(in);
            Path path = httpRequest.getRequestLine().getPath();
            HttpResponse httpResponse = new HttpResponse(out);
            Headers headers = httpRequest.getHeaders();

            if (path.isSameUrlPath("/user/create")) {
                final User user = UserFactory.from(httpRequest);
                DataBase.addUser(user);
                httpResponse.sendRedirect("/index.html");
                return;
            }
            if (path.isSameUrlPath("/user/login")) {
                final HttpBody httpBody = httpRequest.getHttpBody();
                processLogin(httpResponse, httpBody);
                return;
            }
            if (path.isSameUrlPath("/user/list") && headers.isLogin()) {
                Collection<User> users = DataBase.findAll();
                UserList userList = new UserList(new ArrayList<>(users));
                String template = userList.generateUserListTemplate();
                httpResponse.forwardBody(template);
                return;
            }
            if (path.isSameUrlPath("/user/list") && !headers.isLogin()) {
                httpResponse.sendRedirect("/user/login.html");
                return;
            }
            httpResponse.forward(path.getPath());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void processLogin(HttpResponse httpResponse, HttpBody httpBody) throws IOException, URISyntaxException {
        final Contents contents = httpBody.getContents();
        final String userId = contents.getContent("userId");
        final User user = DataBase.findUserById(userId);

        if (user == null) {
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        String password = httpBody.getContents().getContent("password");
        if (user.isSamePassword(password)) {
            httpResponse.responseLoginSuccess();
            return;

        }
        httpResponse.sendRedirect("/user/login_failed.html");
        return;
    }
}
