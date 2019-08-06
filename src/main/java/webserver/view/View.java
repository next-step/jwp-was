package webserver.view;

public class View {
    private byte[] body;

    public View(String body) {
        this.body = body.getBytes();
    }

    public View(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public int getLength() {
        return body.length;
    }
}
