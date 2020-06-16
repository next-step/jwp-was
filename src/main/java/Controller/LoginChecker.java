package Controller;

import db.DataBase;
import http.HttpRequest;
import model.User;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;

public class LoginChecker {

    public static boolean isLogin(@Nonnull HttpRequest httpRequest) {
        return isAuthuorizedUserByCookie(httpRequest) || isAuthuorizedUserByDataBase(httpRequest);
    }

    private static boolean isAuthuorizedUserByCookie(@Nonnull HttpRequest httpRequest) {
        String cookie = httpRequest.getHeaders().get("Cookie");
        if (StringUtils.isEmpty(cookie)) {
            return false;
        }

        String[] splitByEqualSign = cookie.split("=");
        if (splitByEqualSign.length < 2) {
            return false;
        }

        if ("logined".equalsIgnoreCase(splitByEqualSign[0]) && "true".equalsIgnoreCase(splitByEqualSign[1])) {
            return true;
        }

        return false;
    }

    private static boolean isAuthuorizedUserByDataBase(@Nonnull HttpRequest httpRequest) {
        String userId = (String)httpRequest.getQueryValue("userId");
        String password = (String)httpRequest.getQueryValue("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            return false;
        }

        return user.isAuthuorizedUser(userId, password);
    }
}
