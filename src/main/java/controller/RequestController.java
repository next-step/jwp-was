package controller;

import http.QueryString;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    public void createUser(QueryString queryString) {
        User user = new User(queryString);
        logger.info("createUser :{}", user.toString());
    }
}
