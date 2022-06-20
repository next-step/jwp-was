package webserver.http.response;

public class HttpResponse implements Response {

    private byte[] body;

    private int status = 200;

    private String statusMessage = "OK";

    private String location;

    public void redirect302(String location) {
        this.status = 302;
        this.statusMessage = "Found";
        this.location = location;
    }

    public void body(byte[] body) {
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

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String getLocation() {
        return location;
    }
}
