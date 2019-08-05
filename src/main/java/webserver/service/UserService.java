package webserver.service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Parameters;
import webserver.http.RequestLine;

public class UserService implements WebService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void process(Object... obj) {
        RequestLine requestLine = (RequestLine)obj[0];
        Parameters parameters = requestLine.getPath().getParameters();
        User user = User.newInstance(parameters);
        logger.info("user : {}", user.toString());
    }
}
