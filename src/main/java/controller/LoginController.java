package controller;

import db.DataBase;
import model.*;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpSession;
import service.SessionUtils;

public class LoginController extends AbstractController {

    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    public static final String USER_ID = "userId";
    public static final String LOGIN_SUCCESS_PATH = "/index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        final String userId = request.getBody().getFirstValue(USER_ID);
        final User findUser = DataBase.findUserById(userId);
        final Cookie cookie = request.getCookie("sessionId");

        HttpSession httpSession = SessionUtils.getSessionInfo(cookie);

        if (checkLogined(httpSession)) {
            setSuccessResponse(response, httpSession, LOGIN_SUCCESS_PATH);
            return;
        }
        // 등록되지 않은 유저일 경우 로그인 실패
        if (findUser == null) {
            setSuccessResponse(response, httpSession, LOGIN_FAILED_PATH);
            return;
        }
        // 등록된 유저일 경우 세션 정보 등록
        httpSession.setAttribute("logined", "true");
        setSuccessResponse(response, httpSession, LOGIN_SUCCESS_PATH);
    }

    private void setSuccessResponse(HttpResponse response, HttpSession httpSession, String resultPath) {
        response.loginRedirect(resultPath, new Cookie("sessionId", httpSession.getId()));
    }

    private boolean checkLogined(HttpSession httpSession) {
        return httpSession.getAttribute("logined") != null
                && httpSession.getAttribute("logined").equals("true");
    }

}
