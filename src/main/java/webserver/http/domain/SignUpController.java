package webserver.http.domain;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class SignUpController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    private final RequestBody requestBody;

    public SignUpController(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public void execute(RequestLine requestLine, DataOutputStream dos) {
        String userId = requestBody.body("userId");
        DataBase.addUser(new User(userId, requestBody.body("password"), requestBody.body("name"), requestBody.body("email")));
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + "/index.html" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
