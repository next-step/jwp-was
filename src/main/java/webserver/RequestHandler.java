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
import webserver.http.BufferedReaderToHttpRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);

			HttpRequest httpRequest = bufferedReaderToHttpRequest.parsed();
			HttpResponse httpResponse = new HttpResponse(out);

			String path = httpRequest.getPath();
            logger.debug("path : {}", path);

            if (path.endsWith(".html")) {
                processPageRequest(httpResponse, path);
            } else if (containsStaticPath(path)) {
                processStaticRequest(httpResponse, path);
            } else if (path.startsWith("/user/create")) {
                join(httpRequest, httpResponse);
            } else if (path.startsWith("/user/login")) {
                login(httpRequest, httpResponse);
            } else if (path.startsWith("/user/list")) {
                list(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void processPageRequest(HttpResponse httpResponse, String path) throws IOException, URISyntaxException {
        path = "./templates" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
		httpResponse.response200Header(body.length);
		httpResponse.responseBody(body);
    }

    private boolean containsStaticPath(String path) {
        return path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/font") || path.startsWith("/images");
    }

    private void processStaticRequest(HttpResponse httpResponse, String path) throws IOException, URISyntaxException {
        path = "./static" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        if (path.startsWith("./static/css")) {
			httpResponse.response200CssHeader(body.length);
        } else {
			httpResponse.response200Header(body.length);
        }
		httpResponse.responseBody(body);
    }

    private void login(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        User loginUser = DataBase.findUserById(httpRequest.getParameter("userId"));
        logger.debug("로그인 사용자: {}", loginUser);

        boolean logined = false;
        String location = "/user/login_failed.html";

        if (loginUser != null && loginUser.getPassword().equals(httpRequest.getParameter("password"))) {
            logger.debug("로그인 성공: {}", loginUser);
            logined = true;
            location = "/index.html";
        }

		httpResponse.response302LoginedHeader(logined, location);
    }

    private void join(HttpRequest request, HttpResponse httpResponse) {
        User newUser = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        logger.debug("회원가입 사용자 : {}", newUser);
        DataBase.addUser(newUser);

		httpResponse.response302Header();
    }

    private void list(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

		if (httpRequest.getCookie().get("logined").equals("true")) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/profile");

            User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
            String profilePage = template.apply(user);
            logger.debug("ProfilePage : {}", profilePage);

            body = profilePage.getBytes();
        }
		httpResponse.response200Header(body.length);
		httpResponse.responseBody(body);
    }
}
