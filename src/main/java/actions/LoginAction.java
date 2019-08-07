package actions;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.AuthenticationException;
import webserver.EntityNotFoundException;
import webserver.http.Constants;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.response.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class LoginAction implements Action {
    public HttpResponse execute(HttpRequest request) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        try {
            User foundUser = Optional.ofNullable(DataBase.findUserById(userId))
                    .orElseThrow(() -> new EntityNotFoundException(userId));

            if (!foundUser.equalToPassword(password)) {
                throw new AuthenticationException();
            };

            HttpResponse response = new HttpResponse(HttpStatus.FOUND);
            response.addHeader("Location", Constants.HELLO_PAGE);
            response.addCookie("logined", "true; path=/");
            return response;
        } catch (RuntimeException e) {
            HttpResponse response = new HttpResponse(HttpStatus.OK, Constants.TEXT_HTML, getContent(Constants.LOGIN_FAILED_PAGE));
            response.addHeader("Location", Constants.HELLO_PAGE);
            response.addCookie("logined", "false; path=/");
            return response;
        }
    }

    private byte[] getContent(String path) throws IOException, URISyntaxException {
        Path templatePath = Paths.get(Constants.TEMPLATES_PATH, path);
        try {
            return FileIoUtils.loadFileFromClasspath(templatePath.toString());
        } catch (NullPointerException e) {
            throw new FileNotFoundException(path);
        }
    }
}
