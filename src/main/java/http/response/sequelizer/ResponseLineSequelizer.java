package http.response.sequelizer;

import http.response.HttpResponse;

class ResponseLineSequelizer {
    public static String sequelize(HttpResponse httpResponse) {
        return "HTTP/1.1 " + httpResponse.getStatusCode() + " " + httpResponse.getStatusMessage() + "\r\n";
    }
}
