package http.response.sequelizer;

import http.common.Cookies;
import http.common.HeaderFieldName;
import http.response.HttpResponse;
import org.apache.logging.log4j.util.Strings;

class ResponseCookieSequelizer {
    public static String sequelize(HttpResponse httpResponse) {
        Cookies cookies = httpResponse.getCookie();
        if (cookies.isEmpty()) {
            return Strings.EMPTY;
        }

        return HeaderFieldName.SET_COOKIE + ": " + cookies.stringify() + "\r\n";
    }
}
