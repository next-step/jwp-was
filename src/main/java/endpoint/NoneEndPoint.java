package endpoint;

public class NoneEndPoint extends HttpRequestEndpoint {

    public NoneEndPoint() {
        super("");
    }

    @Override
    public void handle() {
    }
}
