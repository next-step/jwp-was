package http.response;

public class HttpResponse400 extends HttpResponse_ {

    public HttpResponse400(String version) {
        super(HttpResponseCode.NOT_FOUNT, version);
    }
}
