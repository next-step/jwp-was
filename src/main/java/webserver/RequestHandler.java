package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.sun.net.httpserver.HttpPrincipal;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
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
                        return request.matchPath("/user/list");
                    }

                    @Override
                    public Response supply(Request request) throws IOException {
                        Cookie cookie = request.getCookie();
                        boolean checkLogin = Boolean.valueOf(cookie.get("logined"));
                        if(checkLogin){
                            TemplateLoader loader = new ClassPathTemplateLoader();
                            loader.setPrefix("/templates");
                            loader.setSuffix(".html");
                            Handlebars handlebars = new Handlebars(loader);

                            Template template = handlebars.compile("user/list");

                            String listPage = template.apply(DataBase.findAll());
                            logger.debug("listPage : {}", listPage);

                            return Response.ok(listPage);
                        }
                        return Response.redirect("/user/login.html");
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
                            logger.info(request.getHeader("Accept"));
                            String accept = request.getHeader("Accept").split(",")[0];
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(accept);
                            body = FileIoUtils.loadFileFromClasspath("./static" + request.getPath());
                            return Response.okWithHeaders(body, headers);
                        }
                        return Response.ok(body);
                    }
                }
                );
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
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}
