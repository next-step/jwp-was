package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        String[] splitQueryString = values[1].split("\\?");
        QueryString queryString = splitQueryString.length == 2 ? QueryStringParser.parse(splitQueryString[1]) : null;

        return new RequestLine(HttpMethod.valueOf(values[0]), splitQueryString[0], queryString, new Protocol(values[2]));
    }

}
