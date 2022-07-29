package service;

import db.DataBase;
import model.ClientResponse;
import model.Credential;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    public ClientResponse createUser(User user) {
        DataBase.addUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("http://localhost:8080/index.html"));
        return new ClientResponse(HttpStatus.FOUND, httpHeaders, user);
    }

    public ClientResponse auth(Credential credential) {
        User user = DataBase.findUserById(credential.getUserId());

        HttpHeaders httpHeaders = new HttpHeaders();
        if (!this.isLoginSuccess(user, credential)) {
            logger.info("login failed ...");
            httpHeaders.setLocation(URI.create("http://localhost:8080/user/login_failed.html"));
            httpHeaders.put(HttpHeaders.SET_COOKIE, List.of(AuthService.LOGIN_HEADER_KEY + "=false; Path=/"));
            return new ClientResponse(HttpStatus.FOUND, httpHeaders, null);
        }

        logger.info("login success ... (userId: {})", user.getUserId());
        httpHeaders.setLocation(URI.create("http://localhost:8080/index.html"));
        httpHeaders.put(HttpHeaders.SET_COOKIE, List.of(AuthService.LOGIN_HEADER_KEY + "=true; Path=/"));
        return new ClientResponse(HttpStatus.FOUND, httpHeaders, null);
    }

    public ClientResponse getUsers() throws IOException {
        boolean logined = AuthService.userLogined.get();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!logined) {
            httpHeaders.setLocation(URI.create("http://localhost:8080/user/login.html"));
            return new ClientResponse(HttpStatus.FOUND, httpHeaders, null);
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        String usersPage = HtmlPageFactory.getUsersPage(users);
        return new ClientResponse(HttpStatus.OK, httpHeaders, usersPage);
    }

    private boolean isLoginSuccess(User user, Credential credential) {
        if (user == null) {
            return false;
        }

        return user.getPassword().equals(credential.getPassword());
    }

}
