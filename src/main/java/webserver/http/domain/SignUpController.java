package webserver.http.domain;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class SignUpController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Override
    public void execute(HttpRequest httpRequest, DataOutputStream dos) {
        RequestBody requestBody = httpRequest.requestBody();

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
