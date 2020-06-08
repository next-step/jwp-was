package http.controller;

import db.DataBase;
import http.*;
import http.enums.HttpResponseCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController extends PathController{

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public LoginController(HttpRequest httpRequest) {
        super(httpRequest);
    }

    public byte[] post() {
        log.info("login controller post method ===========");

        Header headerInfo = new Header();
        headerInfo.addKeyAndValue("Content-Type","text/html;charset=utf-8");

        QueryString requestBodyString = new QueryString(httpRequest.getRequestBody());
        User loginUser = new User(requestBodyString.getParameter("userId"), requestBodyString.getParameter("password"));
        User findUser = DataBase.findUserById(requestBodyString.getParameter("userId"));
        try {
            if (loginUser.equals(findUser)) {
                log.info("login success");
                String resourcePath = ResourcePathMaker.makeTemplatePath("/index.html");
                byte[] responseBody = FileIoUtils.loadFileFromClasspath(resourcePath);
                headerInfo.addKeyAndValue("Set-Cookie", "logined=true; Path=/");

                HttpResponse response = new HttpResponse(HttpResponseCode.OK, responseBody, headerInfo);
                return response.makeResponseBody();
            }
            log.info("login fail");
            String resourcePath = ResourcePathMaker.makeTemplatePath("/user/login_failed.html");
            byte[] responseBody = FileIoUtils.loadFileFromClasspath(resourcePath);
            headerInfo.addKeyAndValue("Set-Cookie", "logined=false");

            HttpResponse response = new HttpResponse(HttpResponseCode.OK, responseBody, headerInfo);
            return response.makeResponseBody();
        } catch(IOException e){
            e.printStackTrace();
        } catch(URISyntaxException e){
            e.printStackTrace();
        }

        return new byte[0];
    }
}
