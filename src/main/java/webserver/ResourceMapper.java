package webserver;

import http.HttpMethod;
import http.RequestMessage;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class ResourceMapper {
    private static final Logger logger = LoggerFactory.getLogger(ResourceMapper.class);
    public static final String NOT_FOUND = "Not Found";

    public static byte[] getResource(RequestMessage requestMessage) {
        if ("/user/create".equals(requestMessage.getPath())) {
            if (requestMessage.getMethod() == HttpMethod.GET) {
                User user = new User(requestMessage.getQueryString());
                return user.toString().getBytes();
            } else if (requestMessage.getMethod() == HttpMethod.POST) {
                User user = new User(requestMessage.getBody());
                return user.toString().getBytes();
            }
        }
        try {
            return FileIoUtils.loadFileFromClasspath("./templates" + requestMessage.getPath());
        } catch (Exception e) {
            return NOT_FOUND.getBytes();
        }
    }
}
