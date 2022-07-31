package webserver.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import webserver.http.Contents;
import webserver.http.HttpBody;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.Path;

public class UserFactory {
    private static final String INVALID_PATH_INFORMATION = "잘못된 PATH 정보";
    private static final String INVALID_HTTP_REQUEST = "잘못된 HTTP REQUEST 정보";
    private static ObjectMapper mapper = new ObjectMapper();

    private UserFactory() {
    }

    public static User from(HttpRequest httpRequest) {
        if (httpRequest == null) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST);
        }
        HttpMethod method = httpRequest.getRequestLine().getMethod();
        if (method.isGet()) {
            final Path path = httpRequest.getRequestLine().getPath();
            return mapper.convertValue(path.getParams(), User.class);
        }
        if (method.isPost()) {
            HttpBody httpBody = httpRequest.getHttpBody();
            Contents contents = httpBody.getContents();
            return mapper.convertValue(contents.getContents(), User.class);
        }
        return null;
    }
}
