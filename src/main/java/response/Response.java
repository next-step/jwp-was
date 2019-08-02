package response;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class Response {
    private String httpStatus;
    private byte[] body;

    public Response(byte[] bytes) {
        this.httpStatus = "200";
        this.body = bytes;
    }

    public Response(String httpStatus, byte[] bytes) {
        this.httpStatus = httpStatus;
        this.body = bytes;
    }

    public static Response of(byte[] bytes) {
        return new Response(bytes);
    }

    public static Response redirect(byte[] bytes) {
        return new Response("302", bytes);
    }

    public int getBodyLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHttpStatus() {
        return httpStatus;
    }
}
