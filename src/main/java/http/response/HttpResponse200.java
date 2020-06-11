package http.response;

public class HttpResponse200 extends HttpResponse_ {

    public HttpResponse200(String version) {
        super(HttpResponseCode.OK, version);
    }
}
