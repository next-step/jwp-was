package webserver.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpStringUtils;
import webserver.ResponseHandler;
import webserver.http.Parameters;
import webserver.http.RequestHeader;
import webserver.http.RequestLine;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserService implements WebService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void process(OutputStream outputStream, Object... obj) {
        RequestLine requestLine = (RequestLine) obj[0];
        RequestHeader requestHeader = (RequestHeader) obj[1];

        String subPath = requestLine.getPath().getSubPath();
        Parameters parameters = requestLine.getPath().getParameters();

        if ("/create".equals(subPath)) {
            createUser(parameters);
            ResponseHandler.response302(outputStream, requestLine);
            return;
        }

        if ("/login".equals(subPath)) {
            String cookie = login(requestLine, parameters);
            ResponseHandler.response302WithCookie(outputStream, requestLine, cookie);
            return;
        }

        if ("/list".equals(subPath)) {
            showUserList(outputStream, requestLine, requestHeader);
            return;
        }
    }

    protected User createUser(Parameters parameters) {
        User user = User.newInstance(parameters);
        DataBase.addUser(user);
        logger.info("register : {}", user.toString());
        return user;
    }

    protected String login(RequestLine requestLine, Parameters parameters) {
        String cookie = "Set-Cookie: ";
        User user = DataBase.findUserById(parameters.findByKey("userId"));

        if (isOwner(parameters, user)) {
            requestLine.getPath().addSubPath("/success");
            logger.info("login success : {} ", user.toString());
            return cookie + "logined=true; Path=/";
        }

        requestLine.getPath().addSubPath("/fail");
        return cookie + "logined=false;";
    }

    protected boolean isOwner(Parameters parameters, User user) {
        return user != null && user.isCorrectPassword(parameters.findByKey("password"));
    }

    protected void showUserList(OutputStream outputStream, RequestLine requestLine, RequestHeader requestHeader) {
        boolean isLogined = HttpStringUtils.checkLoginCookie(requestHeader.findByKey("Cookie"));
        if (!isLogined) {
            requestLine.getPath().addSubPath("/fail");
            ResponseHandler.response200(outputStream, requestLine);
            return;
        }

        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile(requestLine.getPath().getPath());
            List<User> users = DataBase.findAll();
            String listPage = template.apply(users);
            logger.debug("list Page : {}", listPage);

            ResponseHandler.response200WithBody(outputStream, listPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
