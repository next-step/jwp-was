package webserver.servlet;

import db.DataBase;
import dto.SignInUser;
import exception.NotFoundUserException;
import model.User;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionId;
import webserver.http.session.SessionManager;

public class SignInServlet extends HttpServlet {

    private static final String API_PATH = "/user/login";
    private static final String SIGN_IN_SUCCESS_PATH = "/index.html";
    private static final String SIGN_IN_FAILED_PATH = "/user/login_failed.html";

    @Override
    public String getRequestPath() {
        return API_PATH;
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        SignInUser signInUser = convertRequestBodyToSignInUser(request.getBody());

        User user = DataBase.findUserById(signInUser.getUserId())
                .orElseThrow(() -> new NotFoundUserException(signInUser.getUserId()));

        if (user.verifyPassword(signInUser.getPassword())) {
            HttpSession httpSession = createSession(user);

            HttpHeaders httpHeaders = HttpHeaders.redirect(SIGN_IN_SUCCESS_PATH);
            httpHeaders.addResponseHeader(HttpHeader.SET_COOKIE, String.format(SessionManager.SESSION_KEY + "=%s", httpSession.getId()));

            return HttpResponse.redirect(httpHeaders);
        }

        HttpHeaders httpHeaders = HttpHeaders.redirect(SIGN_IN_FAILED_PATH);

        return HttpResponse.redirect(httpHeaders);
    }

    private HttpSession createSession(User user) {
        SessionId sessionId = SessionId.generate();
        HttpSession httpSession = new HttpSession(sessionId);
        httpSession.setAttribute("user", user.getUserId());

        SessionManager.addSession(httpSession);

        return httpSession;
    }

    private SignInUser convertRequestBodyToSignInUser(RequestBody requestBody) {
        String userId = requestBody.getContent("userId");
        String password = requestBody.getContent("password");

        return new SignInUser(userId, password);
    }
}
