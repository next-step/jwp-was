package utils;

import webserver.http.HttpStatus;

public class DefaultPageUtils {

    public static String makeErrorPage(HttpStatus httpStatus) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html>");
        sb.append("<head></head>");
        sb.append("<body><h1>Whitelabel Error Page</h1>");
        sb.append("<div>");
        sb.append(httpStatus.toString());
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}
