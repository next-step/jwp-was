package http;

public class HttpResponse {
    private int statusCode;
    private byte[] responseBody;

    public int getBodyLength() {
        return responseBody.length;
    }

    public byte[] getBody() {
        return responseBody;
    }
}
