package http;

public class RequestLineParser {

    private static final String REQUEST_LINE_TOKENIZER = " ";
    private static final String QUERYSTRING_START_CHARACTER = "\\?";

    public static RequestLine parse(String requestLineStr) {
        String[] value = requestLineStr.split(REQUEST_LINE_TOKENIZER);
        String[] value1 = value[1].split(QUERYSTRING_START_CHARACTER);

        HttpMethod httpMethod = HttpMethod.valueOf(value[0]);
        ProtocolAndVersion protocolAndVersion = new ProtocolAndVersion(value[2]);
        String path = value1[0];

        QueryString queryString = null;
        if(value1.length == 2) {
             queryString = new QueryString(value1[1]);
        }

        return new RequestLine(httpMethod, path, queryString, protocolAndVersion);
    }
}
