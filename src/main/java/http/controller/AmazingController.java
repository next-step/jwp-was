package http.controller;

import db.DataBase;
import http.requests.Cookie;
import http.requests.RequestContext;
import http.responses.HttpStatus;
import http.responses.ResponseContext;
import model.User;
import model.UserParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TemplateReader;
import utils.TemplateRenderer;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 그냥 컨트롤러라고 하면 밋밋해서 으메이징이라고 붙임. 다른 의미로 으미에징해질거 같지만 일단 모르는척..
 */
public class AmazingController {

    private static final Logger log = LoggerFactory.getLogger(AmazingController.class);

    public static ResponseContext dispatch(RequestContext requestContext) {
        final String branch = buildBranchString(requestContext);

        log.debug("branch: {}", branch);

        switch (branch) {
            case "[POST]/user/create":  // 하드코딩 실화니.. =ㅁ=..
                return signUpHandler(requestContext);
            case "[POST]/user/login":   // 실화다..
                return signInHandler(requestContext);
            case "[GET]/user/list":     // 미래의 나야! 잘 치워봐!
                return userListHandler(requestContext);
            default:
                return defaultHandler(requestContext);
        }
    }

    private static ResponseContext userListHandler(RequestContext requestContext) {
        final Cookie cookie = requestContext.getCookie();
        final boolean logined = Boolean.parseBoolean(cookie.getValue("logined"));

        if (logined) {
            final Collection<User> users = DataBase.findAll();
            log.debug("users: {}", users);
            final Map<String, Object> map = new HashMap<>();
            map.put("users", users);
            final String rendered = TemplateRenderer.render(requestContext.getPath(), map);
            log.debug("rendered: {}", rendered);
            return ResponseContext
                    .builder()
                    .status(HttpStatus.OK)
                    .addHeader("Content-Type", "text/html;charset=utf-8")
                    .body(rendered.getBytes())
                    .build();
        }

        return ResponseContext
                .builder()
                .status(HttpStatus.FOUND)
                .addHeader("Location", "/user/login.html")
                .build();
    }

    private static ResponseContext signInHandler(RequestContext requestContext) {
        final String userId = requestContext.getAttributeFromForm("userId");
        final User user = DataBase.findUserById(userId);
        final String password = requestContext.getAttributeFromForm("password");
        final boolean isRightPassword = Optional.ofNullable(user.getPassword()).orElse("").equals(password);
        if (isRightPassword) {
            log.debug("Correct password!");
            return ResponseContext
                    .builder()
                    .status(HttpStatus.FOUND)
                    .addHeader("Set-Cookie", "logined=true; Path=/")
                    .addHeader("Location", "/index.html")
                    .build();
        }
        log.debug("Hacker :'(");
        return ResponseContext
                .builder()
                .status(HttpStatus.FOUND)
                .addHeader("Location", "/user/login_failed.html")
                .build();
    }

    private static ResponseContext signUpHandler(RequestContext requestContext) {
        final User user = UserParser.parse(requestContext);
        DataBase.addUser(user);
        log.debug("user: {}", user);
        log.debug("from db: {}", DataBase.findUserById(user.getUserId()));
        return ResponseContext
                .builder()
                .status(HttpStatus.FOUND)
                .addHeader("Location", "/index.html")
                .build();
    }

    private static ResponseContext defaultHandler(RequestContext requestContext) {
        final byte[] rawBody = convertFileToByte(requestContext.getPath());
        return ResponseContext
                .builder()
                .status(HttpStatus.OK)
                .addHeader("Content-Type", "text/html;charset=utf-8")
                .addHeader("Content-Length", String.valueOf(rawBody.length))
                .body(rawBody)
                .build();
    }

    private static byte[] convertFileToByte(String path) {
        try {
            return TemplateReader.read(path);
        } catch (FileNotFoundException e) {
            return "Hello World".getBytes();
        }
    }

    /**
     * URI로만 분기하면 method를 구분할 수 없어서 분기용으로 일단 이렇게 만듬
     *
     * @param ctx http 요청 컨텍스트
     * @return 분기 문자열
     */
    private static String buildBranchString(RequestContext ctx) {
        return String.format("[%s]%s", ctx.getMethod(), ctx.getPath());
    }
}
