package http;

public class QueryStringParser {

    public static QueryString parse(String s) {
        String[] values = s.split(" ");
        String[] urlparam = values[1].split("\\?");

        return new QueryString(values[0], urlparam[0], urlparam[1], new Protocol(values[2]));
    }
}
