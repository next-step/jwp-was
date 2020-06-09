package user;

import db.DataBase;
import http.Cookie;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author KingCjy
 */
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String LOGINED = "logined";

    public void signUp(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        logger.info("유저 회원가입 = {}", user);
    }

    public boolean login(HttpResponse httpResponse, String userId, String password) {
        User user = DataBase.findUserById(userId);

        if(user == null || !user.verifyPassword(password)) {
            logger.info("로그인 실패 id={}, pw={}", userId, password);
            return false;
        }

        httpResponse.addCookie(new Cookie(LOGINED, "true"));
        logger.info("로그인 성공 id={}, pw={}", userId, password);
        return true;
    }

    public boolean isLoggedIn(HttpRequest httpRequest) {
        Cookie loginCookie = httpRequest.getCookie(LOGINED);

        if(loginCookie == null || !"true".equals(loginCookie.getValue())) {
            return false;
        }
        return true;
    }

    public Collection findAllUsers() {
        return DataBase.findAll();
    }
}
