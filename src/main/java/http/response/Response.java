package http.response;

public class Response {
    private String status;
    private String contentType;
    private String body;

    public Response(String status, String contentType, String body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public Byte[] getBody() {
        return new Byte[0];
    }
}
