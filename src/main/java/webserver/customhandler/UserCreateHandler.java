package webserver.customhandler;

import db.DataBase;
import http.request.Request;
import http.request.body.Body;
import http.request.headers.Headers;
import http.request.requestline.requestLine2.QueryStrings;
import http.response.Response;
import http.response.ResponseLine;
import model.User;
import webserver.Handler;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class UserCreateHandler implements Handler {
    private String url;

    public UserCreateHandler(String url) {
        this.url = url;
    }

    @Override
    public boolean isSameUrl(String url) {
        return this.url.equals(url);
    }

    @Override
    public Response work(Request request) throws UnsupportedEncodingException {
        Map<String, String> body = QueryStrings.parseQueryStrings(request.getBody().getBody());
        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        DataBase.addUser(user);

        return new Response("200 OK", "", "");
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
