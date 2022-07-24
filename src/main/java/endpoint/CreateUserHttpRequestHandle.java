package endpoint;

public class CreateUserHttpRequestHandle extends HttpRequestEndpoint {
    private static final String ENDPOINT = "/user/create";

    public CreateUserHttpRequestHandle() {
        super(ENDPOINT);
    }

    @Override
    public void handle() {

    }
}
