package handler;

import model.HttpHeader;
import model.request.HttpRequestHeader;
import model.response.HttpResponseHeader;
import model.response.ResponseLine;
import service.UserService;
import utils.FileIoUtils;
import utils.parser.HttpHeaderParser;

import java.util.Arrays;
import java.util.List;

public class UserHandler implements PathHandler {
    private static final String USER_PATH = "user";
    private static final String CREATE_REQUEST_PATH = "/user/create";
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

        return new HttpResponseHeader(ResponseLine.httpBadRequest(), null, new byte[0]);
    }
}
