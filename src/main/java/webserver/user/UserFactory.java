package webserver.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import webserver.http.Contents;
import webserver.http.HttpBody;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.Path;

public class UserFactory {
    private static final String INVALID_HTTP_REQUEST = "잘못된 HTTP REQUEST 정보";
    private static final String NOT_SUPPORT_METHOD = "지원하지 않는 메서드";
    private static ObjectMapper mapper = new ObjectMapper();

    private UserFactory() {
    }

    public static User from(HttpRequest httpRequest) {
        if (httpRequest == null) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST);
        }
        final HttpMethod method = httpRequest.getRequestLine().getMethod();
        if (method.isGet()) {
            final Path path = httpRequest.getRequestLine().getPath();
            return mapper.convertValue(path.getParams().getParams(), User.class);
        }
        if (method.isPost()) {
            HttpBody httpBody = httpRequest.getHttpBody();
            Contents contents = httpBody.getContents();
            return mapper.convertValue(contents.getContents(), User.class);
        }
        throw new IllegalStateException(NOT_SUPPORT_METHOD);
    }
}
