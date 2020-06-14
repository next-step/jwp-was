package webserver;

import http.HttpMethod;
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

        if ("/user/create".equals(requestPath)) {
            if (requestMessage.getMethod() == HttpMethod.GET) {
                User user = new User(requestMessage.getQueryString());
                byte[] body = user.toString().getBytes();
                responseMessage.response200Header(body.length);
                responseMessage.responseBody(body);
            } else if (requestMessage.getMethod() == HttpMethod.POST) {
                User user = new User(requestMessage.getBody());
                responseMessage.redirectTo("/index.html");
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
