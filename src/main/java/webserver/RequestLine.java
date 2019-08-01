package webserver;

public class RequestLine {


    public RequestLine(String meth, String value1) {

    }

    public static RequestLine parse(String s) {

        return null;
         String[] values = s.split("");

         return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return "";
    }

    public String getPath() {
        return null;
    }
}
