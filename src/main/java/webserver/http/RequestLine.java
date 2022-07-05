package webserver.http;

public class RequestLine {

    private static final String REQUEST_SEPARATOR = " ";

    private final HttpRequest httpRequest;

    public RequestLine(final String request) {

        final String[] requestInfo = request.split(REQUEST_SEPARATOR);
        requestValidate(requestInfo);

        this.httpRequest = new HttpRequest(requestInfo[0], requestInfo[1], requestInfo[2]);
    }

    private void requestValidate(final String[] requestInfo){

        if(requestInfo.length < 3){
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }

    public HttpRequest toRequest() {
        return this.httpRequest;
    }
}
