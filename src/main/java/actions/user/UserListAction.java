package actions.user;

import db.DataBase;
import enums.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.handler.ModelView;
import webserver.handler.ModelViewActionHandler;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserListAction implements ModelViewActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserListAction.class);

    @Override
    public ModelView actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {

        logger.debug("cookie : {}", httpRequest.getHeader(HttpHeaders.COOKIE));
        if(!StringUtils.nvl(httpRequest.getHeader(HttpHeaders.COOKIE)).contains("logined=true")) {

            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpResponse.setHttpHeader(HttpHeaders.LOCATION, "/user/login.html");
            return null;
        }

        ModelView modelView = new ModelView("user/list");
        modelView.addObject("users", DataBase.findAll());
        return modelView;
    }

}
