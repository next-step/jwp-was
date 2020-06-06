package webserver.customhandler;

import db.DataBase;
import http.request.Request;
import http.request.headers.Headers;
import http.request.requestline.requestLine2.QueryStrings;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import http.response.ResponseBody;
import model.User;
import utils.FileIoUtils;
import webserver.Handler;

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
        Map<String, String> queryStrings = QueryStrings.parseQueryStrings(request.getBody().getBody());
        String userId = queryStrings.get("userId");
        String password = queryStrings.get("password");
        User user;

        HttpStatus status;
        ResponseBody body;
        Map<String, String> headers = new HashMap<>();
        try {
            user = DataBase.findUserById(userId);
        } catch (Exception e) {
            user = new User();
        }

        if (user.getPassword().equals(password)) {
            status = HttpStatus.OK;
            body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
            headers.put("Set-Cookie", "logined=true; Path=/");
            return new Response(status, ContentType.HTML, new Headers(headers), body);
        }

        status = HttpStatus.REDIRECT;
        headers.put("Set-Cookie", "logined=false");
        body = new ResponseBody(FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html"));
        return new Response(status, ContentType.HTML, new Headers(headers), body);
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
