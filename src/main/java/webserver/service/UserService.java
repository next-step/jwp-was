package webserver.service;

import db.DataBase;
import exception.BadRequestException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String ALREADY_EXIST_USER = "이미 존재하는 회원입니다.";

    private final HttpRequest request;

    public UserService(HttpRequest request) {
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
            throw new BadRequestException(ALREADY_EXIST_USER);
        }
    }

    public User findByUserId(String userId) {
        return DataBase.findUserById(userId);
    }

    public List<User> findAllUser() {
        return DataBase.findAll()
                .stream()
                .map(user -> new User(
                        user.getUserId()
                        , user.getPassword()
                        , user.getName()
                        , user.getEmail()))
                .collect(Collectors.toList());
    }
}
