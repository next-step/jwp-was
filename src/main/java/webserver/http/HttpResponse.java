package webserver.http;

import java.util.HashMap;

public class HttpResponse {
    protected HttpHeader httpHeader;
    protected HttpSession httpSession;
    protected String urlPath;
    protected String resultBody;
    protected String redirectUrl;
    protected String cookie;

    public HttpResponse(HttpHeader httpHeader, HttpSession httpSession) {
        this.httpHeader = httpHeader;
        this.httpSession = httpSession;
    }

    public static HttpResponse ok(HttpRequest request){
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.OK, request.getEtcHeader()),
                request.getSession());
        return response;
    }

    public static HttpResponse reDirect(HttpRequest request, String redirectUrl) {
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.REDIRECT, request.getEtcHeader()),
                request.getSession());
        response.redirectUrl = redirectUrl;
        return response;
    }

    private static HttpHeader makeHeader(HttpRequest request, HttpStatus status, HashMap<String, String > etcHeader){
        HttpHeader httpHeader = new HttpHeader(status, request.getVersion(), etcHeader);
        return httpHeader;
    }

    public static HttpResponse serverError(HttpRequest request){
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.OK, request.getEtcHeader()),
                request.getSession());
        return response;
    }

    public static HttpResponse pageNotFound(HttpRequest request){
        HttpResponse response = new HttpResponse(makeHeader(request, HttpStatus.NOT_FOUND, request.getEtcHeader()),
                request.getSession());
        return response;
    }

    public void initResultBody(String urlPath, String resultBody){
        this.urlPath = urlPath;
        this.resultBody = resultBody;
    }

    public void addCookie(String cookie){
        this.cookie = cookie;
    }

    public HttpSession getSession() {
        if(httpSession == null)
            return new HttpSession();

        return httpSession;
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

}
