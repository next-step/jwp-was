package webserver.http.response;

public enum HttpStatus {
    OK(200, "200 OK"),
    REDIRECT(302, "302 Found");

    int status;
    String statusMessage;

    HttpStatus(int status, String statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
