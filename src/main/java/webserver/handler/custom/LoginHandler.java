package webserver.handler.custom;

import db.DataBase;
import http.request.Request;
import http.request.Headers;
import http.request.QueryStrings;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import http.response.ResponseBody;
import model.User;
import utils.FileIoUtils;
import webserver.handler.Handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements Handler {
    private String url;

    public LoginHandler(String url) {
        this.url = url;
    }

    @Override
    public boolean isSameUrl(String url) {
        return this.url.equals(url);
    }

    @Override
    public Response work(Request request) throws IOException, URISyntaxException {
        Map<String, String> queryStrings = QueryStrings.parseQueryStrings(request.getRequestBody().getBody());
        User user = getUser(queryStrings);


        Map<String, String> headers = new HashMap<>();

        if(isSuccess(queryStrings, user)){
            ResponseBody body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
            headers.put("Set-Cookie", "logined=true; Path=/");
            headers.put("Location", "/index.html");
            return new Response(HttpStatus.FOUND, ContentType.HTML, new Headers(headers), body);
        }

        headers.put("Set-Cookie", "logined=false");
        headers.put("Location", "/user/login_failed.html");
        ResponseBody body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html"));
        return new Response(HttpStatus.FOUND, ContentType.HTML, new Headers(headers), body);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    private User getUser(Map<String, String> queryStrings){
        String userId = queryStrings.get("userId");
        User user;

        try {
            user = DataBase.findUserById(userId);
        } catch (Exception e) {
            user = new User();
        }

        return user;
    }

    private boolean isSuccess(Map<String, String> queryStrings, User user){
        String password = queryStrings.get("password");
        return user.getPassword().equals(password);
    }
}
