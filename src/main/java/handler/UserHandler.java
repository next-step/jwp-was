package handler;

import db.DataBase;
import model.HttpHeader;
import model.User;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import model.response.ResponseLine;
import service.UserService;
import utils.FileIoUtils;
import utils.HandleBarCompiler;

import java.util.*;

public class UserHandler implements PathHandler {
    private static final String USER_PATH = "user";
    private static final String CREATE_REQUEST_PATH = "/user/create";
    private static final String LOGIN = "/user/login";
    private static final String FIND_ALL_PATH = "/user/list.html";
    private static final String FIND_ALL_TEMPLATE = "/user/list";
    private static final String LOGIN_PASSED = "logined=true";
    private static final UserService userService = new UserService();

    @Override
    public Boolean canHandling(HttpRequestMessage httpRequestMessage) {
        String[] resources = httpRequestMessage.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return resources[ROOT_RESOURCE_INDEX].equals(USER_PATH);
    }

    @Override
    public HttpResponseMessage Handle(HttpRequestMessage httpRequestMessage) {
        if (httpRequestMessage.isEqualPath(CREATE_REQUEST_PATH)) {
            userService.createUser(httpRequestMessage.getRequestBody());

            HttpHeader httpFoundHeader = new HttpHeader(List.of("Location: http://localhost:8080/index.html"));

            return new HttpResponseMessage(ResponseLine.httpFound(), httpFoundHeader, new byte[0]);
        }

        if (httpRequestMessage.isEqualPath(LOGIN)) {
            return login(httpRequestMessage.getRequestBody());
        }

        if (httpRequestMessage.isEqualPath(FIND_ALL_PATH)) {
            return findAll(httpRequestMessage);
        }

        if (hasResourceIdentifier(httpRequestMessage.getPath())) {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequestMessage.getPath());

            return new HttpResponseMessage(ResponseLine.httpOk(), createOkTemplateHttpHeader(body), body);
        }

        return new HttpResponseMessage(ResponseLine.httpBadRequest(), null, new byte[0]);
    }

    private HttpHeader createOkTemplateHttpHeader(byte[] body) {
        return new HttpHeader(
            Arrays.asList(
                "Content-Type: text/html;charset=utf-8",
                "Content-Length: " + body.length
            ));
    }

    private HttpResponseMessage login(Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User user = userService.findById(userId);

        if (user == null) {
            return new HttpResponseMessage(ResponseLine.httpFound(), createLoginFailHttpHeader(), new byte[0]);
        }

        if (user.login(userId, password)) {
            return new HttpResponseMessage(ResponseLine.httpFound(), createLoginSuccessHttpHeader(), new byte[0]);
        }

        return new HttpResponseMessage(ResponseLine.httpFound(), createLoginFailHttpHeader(), new byte[0]);
    }

    private HttpHeader createLoginFailHttpHeader() {
        return new HttpHeader(
            Arrays.asList(
                "Content-Type: text/html;charset=utf-8",
                "Set-Cookie: logined=false;",
                "Location: http://localhost:8080/user/login_failed.html"
            ));
    }

    private HttpHeader createLoginSuccessHttpHeader() {
        return new HttpHeader(
            Arrays.asList(
                "Content-Type: text/html;charset=utf-8",
                "Set-Cookie: logined=true; Path=/",
                "Location: http://localhost:8080/index.html"
            ));
    }

    private HttpResponseMessage findAll(HttpRequestMessage httpRequestMessage) {
        if ((!validateCookie(httpRequestMessage))) {
            return new HttpResponseMessage(ResponseLine.httpFound(), createNoCookieHttpHeader(), new byte[0]);
        }

        byte[] profileBody = createUserProfileBody();

        return new HttpResponseMessage(ResponseLine.httpOk(), createOkTemplateHttpHeader(profileBody), profileBody);
    }

    private boolean validateCookie(HttpRequestMessage httpRequestMessage) {
        return httpRequestMessage.hasCookie(LOGIN_PASSED);
    }

    private HttpHeader createNoCookieHttpHeader() {
        return new HttpHeader(
            Arrays.asList(
                "Content-Type: text/html;charset=utf-8",
                "Set-Cookie: logined=false;",
                "Location: http://localhost:8080/user/login.html"
            ));
    }

    private byte[] createUserProfileBody() {
        Collection<User> users = DataBase.findAll();

        return HandleBarCompiler.compile(FIND_ALL_TEMPLATE, users).getBytes();
    }
}
