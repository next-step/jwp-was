package webserver.http;

enum Status {

    OK("200", "OK");

    private final String code;

    private final String reason;

    Status(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    String getMessage() {
        return code + " " + reason;
    }
}
