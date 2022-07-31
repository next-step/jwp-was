package webserver.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import webserver.http.Contents;
import webserver.http.HttpBody;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.Params;
import webserver.http.Path;

public class UserFactory {
    private static final String INVALID_PATH_INFORMATION = "잘못된 PATH 정보";
    private static ObjectMapper mapper = new ObjectMapper();

    private UserFactory() {
    }

    public static User from(Path path) {
        if (path == null) {
            throw new IllegalArgumentException(INVALID_PATH_INFORMATION);
        }
        final Params params = path.getParams();
        return mapper.convertValue(params.getParams(), User.class);
    }

    public static User from(HttpRequest httpRequest) {
        if (httpRequest == null) {
            return null;
        }
        HttpMethod method = httpRequest.getRequestLine().getMethod();
        if (method.isGet()) {
            final Path path = httpRequest.getRequestLine().getPath();
            return mapper.convertValue(path.getParams(), User.class);
        }
        if (method.isPost()) {
            HttpBody httpBody = httpRequest.getHttpBody();
            Contents contents = httpBody.getContents();
            String email = contents.getContent("email");

            return mapper.convertValue(contents.getContents(), User.class);
        }
        return new User();
    }
}
