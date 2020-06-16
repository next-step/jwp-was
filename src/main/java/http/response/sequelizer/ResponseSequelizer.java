package http.response.sequelizer;

import http.common.Cookies;
import http.common.HeaderFieldName;
import http.response.HttpResponse;
import org.apache.logging.log4j.util.Strings;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ResponseSequelizer {
    RESPONSE_LINE(ResponseSequelizer::sequelizeResponseLine),
    COOKIE(ResponseSequelizer::sequelizeCookie),
    HEADER(ResponseSequelizer::sequelizeHeader),
    ;

    private Function<HttpResponse, String> sequelizeFunction;

    ResponseSequelizer(Function<HttpResponse, String> sequelizeFunction) {
        this.sequelizeFunction = sequelizeFunction;
    }

    public String sequelize(HttpResponse httpResponse) {
        return this.sequelizeFunction.apply(httpResponse);
    }

    private static String sequelizeResponseLine(HttpResponse httpResponse) {
        return "HTTP/1.1 " + httpResponse.getStatusCode() + " " + httpResponse.getStatusMessage() + "\r\n";
    }

    private static String sequelizeCookie(HttpResponse httpResponse) {
        final Cookies cookies = httpResponse.getCookie();
        return cookies.asList().stream()
                .map(c -> HeaderFieldName.SET_COOKIE.stringify() + ": " + c + "\r\n")
                .collect(Collectors.joining());
    }

    private static String sequelizeHeader(HttpResponse httpResponse) {
        final StringBuffer sb = new StringBuffer();
        for (Iterator it = httpResponse.getHeader().iterator(); it.hasNext(); ) {
            String headerName = (String) it.next();
            String headerValue = httpResponse.getHeader(headerName).orElse(Strings.EMPTY);
            sb.append(headerName + ": " + headerValue + "\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }

}
