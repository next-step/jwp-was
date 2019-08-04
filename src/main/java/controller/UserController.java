package controller;

import exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import webserver.http.request.Query;
import webserver.http.request.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class UserController implements Controller {

    private static UserController userController;

    private static final Map<String, Function<Query, String>> controller = new HashMap<>();

    static {
        controller.put("/", index());
        controller.put("/user/create", createUser());
    }

    private static Function<Query, String> index() {
        return query -> "index.html";
    }

    private static Function<Query, String> createUser() {
        return query -> {
            User user = User.builder()
                    .userId(query.get("userId"))
                    .password(query.get("password"))
                    .name(query.get("name"))
                    .email(query.get("email"))
                    .build();

            log.info("user object : {}", user);

            return "/user/create";
        };
    }

    @Override
    public String get(Request request) {
        try {
            return controller.get(request.path())
                             .apply(request.query()); // 차후 query 또는 request body 둘다 처리 가능하도록 변경하자
        } catch (NullPointerException e) {
            throw new NotFoundException(String.format("경로를 찾을 수 없습니다. 입력값 : %s", request.path()));
        }
    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }

        return userController;
    }

    private UserController() {}
}
