package webserver.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
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
}
