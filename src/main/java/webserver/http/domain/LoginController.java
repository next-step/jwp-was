package webserver.http.domain;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class LoginController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final RequestBody requestBody;
    public LoginController(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public void execute(RequestLine requestLine, DataOutputStream dos) {
        String userId = requestBody.body("userId");
        User user = DataBase.findUserById(userId);

        if (user != null && user.samePassword(requestBody.body("password"))) {
            try {
                dos.writeBytes("HTTP/1.1 302 Found \r\n");
                dos.writeBytes("Set-Cookie: logined=true; Path=/ \r\n");
                dos.writeBytes("Location: /index.html\r\n");
                dos.writeBytes("\r\n");
//                dos.write(body, 0, body.length);
                dos.flush();
                return;
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Set-Cookie: logined=false; Path=/ \r\n");
            dos.writeBytes("Location: /user/login_failed.html\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
