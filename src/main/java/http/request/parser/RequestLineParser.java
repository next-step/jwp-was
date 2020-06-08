package http.request.parser;

import http.request.HttpMethod;
import http.common.ProtocolAndVersion;
import http.request.QueryString;
import http.request.RequestLine;

public class RequestLineParser {

    private static final String REQUEST_LINE_TOKENIZER = " ";
    private static final String QUERYSTRING_START_CHARACTER = "\\?";

    public static RequestLine parse(String requestLineStr) {
        String[] value = requestLineStr.split(REQUEST_LINE_TOKENIZER);
        String[] pathAndQuerystring = value[1].split(QUERYSTRING_START_CHARACTER);

        HttpMethod httpMethod = HttpMethod.valueOf(value[0]);
        ProtocolAndVersion protocolAndVersion = new ProtocolAndVersion(value[2]);
        String path = pathAndQuerystring[0];

        String queryStringStr = "";
        if (pathAndQuerystring.length == 2) {
            queryStringStr = pathAndQuerystring[1];
        }

        QueryString queryString = new QueryString(queryStringStr);

        return new RequestLine(httpMethod, path, queryString, protocolAndVersion);
    }
}
