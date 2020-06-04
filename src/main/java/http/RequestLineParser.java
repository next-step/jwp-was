package http;

public class RequestLineParser {

    public static RequestLine parse(String requestLineStr) {
        String[] value = requestLineStr.split(" ");
        HttpMethod httpMethod = HttpMethod.valueOf(value[0]);
        String[] value1 = value[1].split("\\?");
        Protocol protocol = new Protocol(value[2]);

        QueryString queryString = null;
        if(value1.length == 2) {
             queryString = new QueryString(value1[1]);
        }

        return new RequestLine(httpMethod, value1[0], protocol, queryString);
    }
}
