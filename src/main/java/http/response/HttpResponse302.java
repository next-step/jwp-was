package http.response;

public class HttpResponse302 extends HttpResponse_ {

    public HttpResponse302(String path, String version) {
        super(HttpResponseCode.FOUND, version);
        this.addHeader("Location", path);
    }
}
