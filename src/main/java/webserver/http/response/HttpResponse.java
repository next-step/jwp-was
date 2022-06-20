package webserver.http.response;

public class HttpResponse implements Response {

    private byte[] body;

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public int getLength() {
        return body == null ? 0 : body.length;
    }

    @Override
    public byte[] getBody() {
        return body;
    }
}
