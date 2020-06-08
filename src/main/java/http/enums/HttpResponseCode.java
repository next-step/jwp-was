package http.enums;

import http.Const.HttpConst;

/**
 * Created By kjs4395 on 2020-06-05
 */
public enum HttpResponseCode {
    OK(200, "OK"), REDIRECT(302, "Found");
    Integer code;
    String message;


    HttpResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String makeHeader() {
        return "HTTP/1.1 " + this.code.toString() + " " + this.message + HttpConst.CRLF;
    }
}
