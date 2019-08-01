package webserver.http;

public class RequestLine {

    public String url;

    public RequestLine(String url){
        this.url = url;
    }

    public static RequestLine parse(String url){
        return new RequestLine(url);
    }

    public String getMethod(){
        return strSplit()[0];
    }

    public String getPath(){
        return strSplit()[1];
    }

    private String[] strSplit(){
        return url.split(" ");
    }

}
