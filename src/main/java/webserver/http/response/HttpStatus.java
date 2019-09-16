package webserver.http.response;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found");

    HttpStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    private int code;
    private String reasonPhrase;

    public String line() {
        return this.code + " " + this.reasonPhrase;
    }
}
