package webserver;

import com.sun.net.httpserver.HttpPrincipal;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private List<Servlet> servlets;

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.servlets = Arrays.asList(
                new Servlet() {
                    @Override
                    public boolean isMapping(Request request) {
                        return request.matchPath("/user/create");
                    }

                    @Override
                    public Response supply(Request request) {
                        String userId = request.getParameter("userId");
                        String password = request.getParameter("password");
                        String name = request.getParameter("name");
                        String email = request.getParameter("email");
                        DataBase.addUser(new User(userId, password, name, email));
                        logger.info("allUsers {}", DataBase.findAll());

                        return Response.redirect("/index.html");
                    }
                },
                new Servlet() {
                    @Override
                    public boolean isMapping(Request request) {
                        return request.matchPath("/user/login");
                    }

                    @Override
                    public Response supply(Request request) {
                        String userId = request.getParameter("userId");
                        String password = request.getParameter("password");
                        User userById = DataBase.findUserById(userId);

                        HttpHeaders httpHeaders = new HttpHeaders();
                        if (userById == null || !userById.checkPassword(password)) {
                            httpHeaders.add("Set-Cookie: logined=false; Path=/");
                            return Response.redirect("/user/login_failed.html");
                        }
                        httpHeaders.add("Set-Cookie: logined=true; Path=/");
                        return Response.redirectWithHeaders("/index.html", httpHeaders);
                    }
                },
                new Servlet() {
                    @Override
                    public boolean isMapping(Request request) {
                        return request.isGet();
                    }

                    @Override
                    public Response supply(Request request) throws Exception {
                        String path = request.getPath();
                        String suffix = path.substring(path.lastIndexOf(".") + 1);

                        byte[] body = null;
                        if (suffix.equals("html") || suffix.equals("ico")) {
                            body = FileIoUtils.loadFileFromClasspath("./templates" + request.getPath());
                        } else {
                            body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
                        }
                        return Response.ok(body);
                    }
                });
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            Request request = Request.of(in);
            logger.info("IN request: {}", request);

            Servlet servlet = this.servlets.stream()
                    .filter(it -> it.isMapping(request))
                    .findFirst()
                    .get();

            Response response = servlet.supply(request);
            response.send(out);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
