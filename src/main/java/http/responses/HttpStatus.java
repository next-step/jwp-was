package http.responses;

/**
 * 다쓰기 귀찮으니까 사용할 것만 일단 추가하자 =ㅅ=
 */
public enum HttpStatus {

    OK(200, "Ok"),

    FOUND(302, "Found"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
    ;

    private int statusCode;
    private String reasonPhrase;

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    HttpStatus(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }
}
