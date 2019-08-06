package webserver.http;

public class RequestLine {
    public HttpRequest httpRequest;

    public RequestLine(HttpController httpController, String httpStr){
        httpRequest = new HttpRequest(httpController, httpStr);
    }

    public static RequestLine parse(HttpController httpController, String url){
        return new RequestLine(httpController, url);
    }

    public HttpRequest getHttpRequest(){
        return httpRequest;
    }
}
