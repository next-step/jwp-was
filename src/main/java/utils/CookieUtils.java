package utils;

import org.checkerframework.checker.units.qual.C;
import request.Cookie;


public class CookieUtils {

    /**
     * 로그인 성공 시 쿠키 저장
     */
    public static Cookie setCookie(boolean isLoginSuccess) {
        return makeCookie(isLoginSuccess);
    }

    private static Cookie makeCookie(boolean isLoginSuccess) {
        if (isLoginSuccess) {
            return new Cookie("logined", "true");
        }
        return new Cookie("logined", "false");
    }
}
