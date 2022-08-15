package webserver.http.response;

public class ResponseBody {
    private byte[] responseBody;

    public ResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public int getContentLength() {
        return responseBody.length;
    }
}
