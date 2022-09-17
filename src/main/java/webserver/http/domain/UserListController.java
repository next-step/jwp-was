package webserver.http.domain;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.template.UserList;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserListController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private final RequestHeader requestHeader;
    public UserListController(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    public void execute(RequestLine requestLine, DataOutputStream dos) {
        if (requestHeader.loginCheck()) {
            Collection<User> users = DataBase.findAll();
            UserList userList = new UserList(new ArrayList<>(users));
            String template = userList.generateUserListTemplate();
            byte[] body = template.getBytes();
            try {
                int bodyLength = body.length;
                dos.writeBytes("HTTP/1.1 200 OK \r\n");
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + bodyLength + "\r\n");
                dos.writeBytes("\r\n");
                dos.write(body, 0, bodyLength);
                dos.flush();
                return ;
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + "/user/login.html" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
