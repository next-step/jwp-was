package service;

import db.DataBase;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.HttpStatus;
import types.MediaType;
import utils.HtmlPageFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final UserService userService = new UserService();

    private UserService() {

    }

    public static UserService getInstance() {
        return userService;
    }

    public HttpResponseMessage createUser(User user) throws IOException {
        DataBase.addUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        httpHeaders.setLocation(URI.create("http://localhost:8080/index.html").toString());
        return new HttpResponseMessage(HttpStatus.FOUND, httpHeaders, user);
    }

    public HttpResponseMessage auth(HttpHeaders requestHeaders, Credential credential) {
        User user = DataBase.findUserById(credential.getUserId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        if (!this.isLoginSuccess(user, credential)) {
            logger.info("login failed ...");
            httpHeaders.setLocation(URI.create("http://localhost:8080/user/login_failed.html").toString());
            return new HttpResponseMessage(HttpStatus.FOUND, httpHeaders);
        }

        logger.info("login success ... (userId: {})", user.getUserId());
        httpHeaders.setLocation(URI.create("http://localhost:8080/index.html").toString());
        Session session = SessionStorage.getInstance().getSession(requestHeaders.getSessionId());
        if (session == null) {
            throw new IllegalAccessError();
        }
        session.setUserLogined();
        return new HttpResponseMessage(HttpStatus.FOUND, httpHeaders);
    }

    public HttpResponseMessage getUsers() throws IOException {
        boolean logined = AuthService.userLogined.get();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        if (!logined) {
            httpHeaders.setLocation(URI.create("http://localhost:8080/user/login.html").toString());
            return new HttpResponseMessage(HttpStatus.FOUND, httpHeaders);
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        String usersPage = HtmlPageFactory.getUsersPage(users);
        return new HttpResponseMessage(HttpStatus.OK, httpHeaders, usersPage);
    }

    private boolean isLoginSuccess(User user, Credential credential) {
        if (user == null) {
            return false;
        }

        return user.getPassword().equals(credential.getPassword());
    }

}
