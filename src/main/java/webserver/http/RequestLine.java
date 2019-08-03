package webserver.http;

public class RequestLine {
    public HttpRequest httpRequest;

    public String httpMsg;


    public RequestLine(String httpMsg){
        this.httpMsg = httpMsg;
        httpRequest = new HttpRequest(httpMsg);

    }

    public static RequestLine parse(String url){
        return new RequestLine(url);
    }

    public String getMethod(){
        return httpRequest.getHttpMethod();
    }

    public String getPath(){
        return httpRequest.getHttpPath();
    }

    public String getParameter(String keyStr){
        return httpRequest.getHttpParameterValue(keyStr);
    }


}
