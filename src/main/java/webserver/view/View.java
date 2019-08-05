package webserver.view;

public class View {
    private String body;

    public View(String body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body.getBytes();
    }

    public int getLength() {
        return body.getBytes().length;
    }
}
