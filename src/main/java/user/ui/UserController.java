package user.ui;

import http.RequestLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final RequestLine requestLine;

    public UserController(final RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public byte[] mapping() {
        String method = requestLine.getMethodName();

        if (method.equals("GET")) {
            return mappingByGetMethod();
        }
        return mappingByPostMethod();
    }

    private byte[] mappingByGetMethod() {
        String path = requestLine.getPath();
        if (path.equals("/user/form.html")) {
            return getUserForm(String.format("./templates/%s", path));
        }

        if (path.equals("/user/create")) {
            return "test".getBytes();
        }

        return null;
    }

    private byte[] mappingByPostMethod() {
        String path = requestLine.getPath();
        if (path.equals("/user/create")) {
            Map<String, String> requestParams = requestLine.getParams();
            String userId = requestParams.getOrDefault("userId", "");
            String password = requestParams.getOrDefault("password", "");
            String email = requestParams.getOrDefault("email", "");
            String name = requestParams.getOrDefault("name", "");
            User user = new User(userId, password, email, name);
            logger.debug(user.toString());
            return user.toString().getBytes();
        }

        return null;
    }

    private byte[] getUserForm(String filePath) {
        try {
            return viewByUserForm(filePath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Not Found Path");
        }
    }

    private byte[] viewByUserForm(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }
}
