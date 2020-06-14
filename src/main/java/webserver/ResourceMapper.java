package webserver;

import db.DataBase;
import http.HttpMethod;
import http.QueryString;
import http.RequestMessage;
import http.ResponseMessage;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class ResourceMapper {
    private static final Logger logger = LoggerFactory.getLogger(ResourceMapper.class);

    public static void service(RequestMessage requestMessage, ResponseMessage responseMessage) {
        String requestPath = requestMessage.getPath();
        HttpMethod method = requestMessage.getMethod();

        if ("/user/create".equals(requestPath)) {
            if (method == HttpMethod.GET) {
                User user = new User(requestMessage.getQueryString());
                byte[] body = user.toString().getBytes();

                responseMessage.response200Header(body.length);
                responseMessage.responseBody(body);
            } else if (method == HttpMethod.POST) {
                User user = new User(requestMessage.getBody());
                DataBase.addUser(user);

                responseMessage.redirectTo("/index.html");
            }
        } else if ("/user/login".equals(requestPath)) {
            if (method == HttpMethod.POST) {
                QueryString requestBody = requestMessage.getBody();
                User loginUser = DataBase.findUserById(requestBody.getFirstParameter("userId"));
                if (loginUser == null) {
                    responseMessage.setHeader("Set-Cookie", "logined=false");
                    responseMessage.redirectTo("/user/login_failed.html");
                } else {
                    responseMessage.setHeader("Set-Cookie", "logined=true");
                    responseMessage.redirectTo("/index.html");
                }
            }
        } else {
            try {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestPath);

                responseMessage.response200Header(body.length);
                responseMessage.responseBody(body);
            } catch (Exception e) {
                String reason = requestPath + " is not found";
                byte[] body = reason.getBytes();

                responseMessage.response404Header();
                responseMessage.responseBody(body);
            }
        }
    }
}
