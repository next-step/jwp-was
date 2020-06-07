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
        this.requestLine= requestLine;
    }

    public byte[] mapping() {
        String path = requestLine.getPath();
        if (path.equals("/user/form.html")) {
            return getUserForm(String.format("./templates/%s", path));
        }

        if (path.equals("/user/create")) {
            final Map<String, String> params = requestLine.getParams();
            User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
            logger.debug(user.toString());
            return "test".getBytes();
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
