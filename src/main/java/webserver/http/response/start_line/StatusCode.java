package webserver.http.response.start_line;

public enum StatusCode {
    OK(200),
    FOUND(302),
    NOT_FOUND(404);


    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
