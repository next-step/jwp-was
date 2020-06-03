package http;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLineParser {

    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        return new RequestLine(values[0], values[1], new Protocol(values[2]));
    }
}
