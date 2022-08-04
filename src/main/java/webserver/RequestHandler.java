package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.request.domain.request.*;
import webserver.response.HttpResponse;
import webserver.util.HandlebarsObject;


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
            if (in != null) {
                HttpRequest httpRequest = new HttpRequest(in);
                HttpResponse httpResponse = new HttpResponse(out);
                matchResponse(httpRequest, httpResponse);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void matchResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        Method method = httpRequest.getMethod();

        if (path.equals("/user/create")) {
            if(method.isPost()) {
                RequestBody requestBody = httpRequest.getBody();
                httpResponse.redirect(parseBody(path, requestBody));
            }
        } else if (path.equals("/user/login")) {
            if (isLogin(httpRequest)) {
                httpResponse.loginRedirect("/index.html", "logined=true Path=/");
            } else {
                httpResponse.loginRedirect("/user/login_failed.html", "logined=false");
            }
        } else if (path.equals("/user/list")) {
            String headerValue = httpRequest.getHeader("Cookie");
            if (headerValue == null) {
                httpResponse.forward(IOUtils.loadFileFromClasspath("./templates/user/login.html"));
            } else if (headerValue.equals("logined=true Path=/")) {
                HandlebarsObject handlebarObj = HandlebarsObject.createHandlebarObject("/templates", ".html");

                Collection<User> users = DataBase.findAll();
                httpResponse.forward(handlebarObj.applyTemplate("user/list", users));
            } else if (headerValue.equals("logined=false")){
                httpResponse.forward(IOUtils.loadFileFromClasspath("./templates/user/login.html"));
            }
        } else {
            if (path.endsWith("html")) {
                httpResponse.forward(IOUtils.loadFileFromClasspath("./templates" + path));
            } else if (path.endsWith("css")) {
                httpResponse.forwardCSS(IOUtils.loadFileFromClasspath("./static" + path));
            }
        }
    }

    private boolean isLogin(HttpRequest httpRequest) {
        String userId = httpRequest.getBody().getParameter("userId");
        User user = DataBase.findUserById(userId);

        if(user == null) {
            return false;
        }

        String pw = httpRequest.getBody().getParameter("password");

        return user.getPassword().equals(pw);
    }

//    private String parseQueryString(String parsePath, QueryString queryString) {
//        if (queryString != null) {
//            String[] str = parsePath.split("/");
//            if (str.length > 2) {
//                if (str[1].equals("user")) {
//                    userService(queryString.getDataPairs(), str);
//                }
//            }
//        }
//
//        return "/index.html";
//    }

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
