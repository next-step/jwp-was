package webserver.http;

public class HttpResponse {
    protected HttpHeader httpHeader;
    protected String urlPath;
    protected String resultBody;
    protected String redirectUrl;
    protected String cookie;

    public HttpResponse(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    public static HttpResponse ok(HttpRequest request){
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.OK));
        return response;
    }

    public static HttpResponse reDirect(HttpRequest request, String redirectUrl) {
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.REDIRECT));
        response.setRedirectUrl(redirectUrl);
        return response;
    }

    private static HttpHeader makeHeader(HttpRequest request, HttpStatus status){
        HttpHeader httpHeader = new HttpHeader(status, request.getVersion());
        httpHeader.setEtcHeader(request.getEtcHeader());
        return httpHeader;
    }

    public static HttpResponse serverError(HttpRequest request){
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.OK));
        return response;
    }

    public static HttpResponse pageNotFound(HttpRequest request){
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.NOT_FOUND));
        return response;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpStatus getHttpStatus() { return httpHeader.getResultCode(); }

    public int getStatusCode() {
        return httpHeader.getResultCode().getHttpStatusCode();
    }

    public String getUrlPath(){ return urlPath; }

    public String getResultBody() {
        return resultBody;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getCookie() {
        return cookie;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public void setResultBody(String resultBody) {
        this.resultBody = resultBody;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

}
