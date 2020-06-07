package http;

public class RequestLineParser {

    public static RequestLine parse(String requestLine) {
        String[] requestLineElements = requestLine.split(" ");
        return new RequestLine(requestLineElements[0], requestLineElements[1], requestLineElements[2]);
    }

}
