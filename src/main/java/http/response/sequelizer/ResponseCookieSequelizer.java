package http.response.sequelizer;

import http.common.Cookies;
import http.common.HeaderFieldName;
import http.response.HttpResponse;
import org.apache.logging.log4j.util.Strings;

class ResponseCookieSequelizer {
    private static final String RESPONSE_COOKIE_DEFAULT_VALUE = Strings.EMPTY;

    public static String sequelize(HttpResponse httpResponse) {
        Cookies cookies = httpResponse.getCookie();
        if (cookies.isEmpty()) {
            return RESPONSE_COOKIE_DEFAULT_VALUE;
        }

        return HeaderFieldName.SET_COOKIE + ": " + cookies.stringify() + "\r\n";
    }
}
