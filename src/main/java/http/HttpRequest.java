package http;

import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;

    public HttpRequest(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public String getPath(){
        return requestLine.getPath();
    }

    public String getParameter(String param){
        return requestLine.getQueryString().getParameter(param);
    }

}
