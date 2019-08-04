package webserver.http;

import webserver.domain.HttpParseVO;

public class RequestLine {
    public HttpRequest httpRequest;

    public RequestLine(String httpStr){
        httpRequest = new HttpRequest(httpStr);
    }

    public static RequestLine parse(String url){
        return new RequestLine(url);
    }

    public HttpParseVO getParseResult(){
        return httpRequest.getHttpParseVO();
    }

    public String getParam(String param) {
        return httpRequest.getParameterQuery(param);
    }
}
