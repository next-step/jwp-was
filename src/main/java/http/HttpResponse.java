package http;

public class HttpResponse {
    int statusCode;
    byte[] responseBody;

    public int getBodyLength() {
        return responseBody.length;
    }

    public byte[] getBody() {
        return responseBody;
    }
}
