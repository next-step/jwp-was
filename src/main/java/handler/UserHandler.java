package handler;

import model.HttpHeader;
import model.User;
import model.request.HttpRequestHeader;
import model.response.HttpResponseHeader;
import model.response.ResponseLine;
import service.UserService;
import utils.FileIoUtils;
import utils.parser.HttpHeaderParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserHandler implements PathHandler {
    private static final String USER_PATH = "user";
    private static final String CREATE_REQUEST_PATH = "/user/create";
    private static final String LOGIN = "/user/login";
    private static final UserService userService = new UserService();

    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return resources[ROOT_RESOURCE_INDEX].equals(USER_PATH);
    }

    @Override
    public HttpResponseHeader Handle(HttpRequestHeader httpRequestHeader) {
        if (hasTemplateIdentifier(httpRequestHeader)) {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequestHeader.getPath());
            HttpHeader httpOkHeader = HttpHeaderParser.parseHeader(
                Arrays.asList(
                    "Content-Type: text/html;charset=utf-8",
                    "Content-Length: " + body.length
                ));

            return new HttpResponseHeader(ResponseLine.httpOk(), httpOkHeader, body);

        }

        if (httpRequestHeader.isEqualPath(CREATE_REQUEST_PATH)) {
            userService.createUser(httpRequestHeader.getRequestBody());

            HttpHeader httpFoundHeader = HttpHeaderParser.parseHeader(List.of("Location: http://localhost:8080/index.html"));

            return new HttpResponseHeader(ResponseLine.httpFound(), httpFoundHeader, new byte[0]);
        }

        if (httpRequestHeader.isEqualPath(LOGIN)) {
            return login(httpRequestHeader.getRequestBody());
        }

        return new HttpResponseHeader(ResponseLine.httpBadRequest(), null, new byte[0]);
    }

    private HttpResponseHeader login(Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User user = userService.findById(userId);

        if (user == null) {
            return new HttpResponseHeader(ResponseLine.httpFound(), createLoginFailHttpHeader(), new byte[0]);
        }

        if (user.login(userId, password)) {
            return new HttpResponseHeader(ResponseLine.httpOk(), createLoginSuccessHttpHeader(), new byte[0]);
        }

        return new HttpResponseHeader(ResponseLine.httpFound(), createLoginFailHttpHeader(), new byte[0]);
    }

    private HttpHeader createLoginFailHttpHeader() {
        return HttpHeaderParser.parseHeader(
            Arrays.asList(
                "Content-Type: text/html;charset=utf-8",
                "Set-Cookie: logined=false;",
                "Location: http://localhost:8080/user/login_failed.html"
            ));
    }

    private HttpHeader createLoginSuccessHttpHeader() {
        return HttpHeaderParser.parseHeader(
            Arrays.asList(
                "Content-Type: text/html;charset=utf-8",
                "Set-Cookie: logined=true; Path=/",
                "Location: http://localhost:8080/index.html"
            ));
    }
}
