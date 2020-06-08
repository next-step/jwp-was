package http.response.sequelizer;

import http.response.HttpResponse;

import java.util.Iterator;

class ResponseHeaderSequelizer {
    public static String sequelize(HttpResponse httpResponse) {
        StringBuffer sb = new StringBuffer();
        for (Iterator it = httpResponse.getHeader().iterator(); it.hasNext(); ) {
            String headerName = (String) it.next();
            String headerValue = httpResponse.getHeader(headerName);
            sb.append(headerName + ": " + headerValue + "\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
