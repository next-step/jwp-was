package webserver.domain;

import java.util.Arrays;

public enum HttpStatus {
    SUCESS("OK", 200),
    REDIRECT("Found", 302),
    NOT_FOUND("Not Found", 404),
    SERVER_ERROR("Server Error", 500);

    String httpStatusMessage;
    int httpStatusCode;

    HttpStatus(String httpStatusMessage, int httpStatusCode) {
        this.httpStatusMessage = httpStatusMessage;
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpStatusMessage() {
        return httpStatusMessage;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public static String getHttpStatusMessage(int httpStatusCode){
        return Arrays.stream(HttpStatus.values())
                .filter(filter -> filter.httpStatusCode == httpStatusCode)
                .findFirst()
                .orElse(HttpStatus.NOT_FOUND)
                .getHttpStatusMessage();
    }

    public static void setRedirect(HttpParseVO httpParseVO, String location){
        httpParseVO.setResultCode(HttpStatus.REDIRECT.getHttpStatusCode());
        httpParseVO.setReturnContent(HttpStatus.REDIRECT.getHttpStatusMessage());
        httpParseVO.setLocation(location);
    }

    public static void setPageNotFond(HttpParseVO httpParseVO){
        httpParseVO.setResultCode(HttpStatus.NOT_FOUND.getHttpStatusCode());
        httpParseVO.setReturnContent(HttpStatus.NOT_FOUND.getHttpStatusMessage());
    }

    public static void setPageError(HttpParseVO httpParseVO){
        httpParseVO.setResultCode(HttpStatus.SERVER_ERROR.getHttpStatusCode());
        httpParseVO.setReturnContent(HttpStatus.SERVER_ERROR.getHttpStatusMessage());
    }
}
