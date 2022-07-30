package webserver.http.response.post;

import db.DataBase;
import model.LoginRequest;

public class PostUserLoginResponse {

    public static final String LOGIN_REDIRECT_HTML = "/index.html";


    public boolean response(String requestBody) {
        LoginRequest request = UserParser.parseToLoginRequest(requestBody);
        return DataBase.existsUser(request.getUserId()) && matchPassword(request);
    }

    private boolean matchPassword(LoginRequest request) {
        return DataBase.findUserById(request.getUserId()).getPassword().equals(request.getPassword());
    }
}
