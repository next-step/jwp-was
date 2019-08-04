package webserver.http;

public enum HttpStatusCode {

    OK(200);

    private int statusCode;

    HttpStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return name();
    }
}