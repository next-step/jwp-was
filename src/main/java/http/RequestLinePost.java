package http;

public class RequestLinePost extends RequestLine {
    public RequestLinePost(String[] tokens) {
        super(Method.POST, "", "");
    }
}
