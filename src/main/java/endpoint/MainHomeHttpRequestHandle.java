package endpoint;

public class MainHomeHttpRequestHandle extends HttpRequestEndpoint {
    private static final String ENDPOINT = "/index.html";

    public MainHomeHttpRequestHandle() {
        super(ENDPOINT);
    }

    @Override
    public void handle() {

    }
}
