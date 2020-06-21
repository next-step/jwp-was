package http;

public enum HttpResponseCode {
    OK_200("HTTP/1.1 200 OK \r\n"),
    REDIRECT_300("HTTP/1.1 302 Found \n");

    private String headerLine;

    HttpResponseCode(String headerLine) {
        this.headerLine = headerLine;
    }


    public static String getHeaderLine(HttpResponseCode code) {
        return code.headerLine;
    }
}
