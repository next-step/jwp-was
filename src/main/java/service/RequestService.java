package service;

import controller.RequestController;
import db.DataBase;
import exception.BadRequestException;
import exception.UserNotFoundException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Cookie;
import request.Request;
import utils.CookieUtils;


import java.util.Objects;

public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);
    private static final String NOT_FOUND_USER = "해당 유저가 존재하지 않습니다.";
    private static final String INVALID_ID_PASSWORD = "아이디 또는 패스워드가 일치하지 않습니다.";

    private final Request request;

    public RequestService(Request request) {
        this.request = request;
    }

    public String saveMember() {

        User user = request.convertUserOfQueryParam();
        validate(user);
        DataBase.addUser(user);
        logger.debug("회원 저장 성공: {}", user.getUserId());
        return user.getUserId();
    }

    private void validate(User user) {
        if (Objects.nonNull(findByUserId(user.getUserId()))) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
    }

    public User findByUserId(String userId) {
        return DataBase.findUserById(userId);
    }

    public Cookie checkIdAndPassword(User requestUser) {
        logger.debug("request user : {}", requestUser);
        User savedUser = findByUserId(requestUser.getUserId());
        if (Objects.isNull(savedUser) ||
                !savedUser.getPassword().equals(requestUser.getPassword())) {
            logger.debug("cookie 로그인 실패");
            return CookieUtils.setCookie(false);
        }

        logger.debug("cookie 로그인 성공");
        return CookieUtils.setCookie(true);
    }
}