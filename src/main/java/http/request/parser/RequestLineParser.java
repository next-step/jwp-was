package http.request.parser;

import http.request.HttpMethod;
import http.common.ProtocolAndVersion;
import http.request.Parameters;
import http.request.RequestLine;

public class RequestLineParser {

    private static final String REQUEST_LINE_TOKENIZER = " ";
    private static final String QUERYSTRING_START_CHARACTER = "\\?";

    public static RequestLine parse(String requestLineStr) {
        final String[] value = requestLineStr.split(REQUEST_LINE_TOKENIZER);
        final String[] pathAndQuerystring = value[1].split(QUERYSTRING_START_CHARACTER);

        final HttpMethod httpMethod = HttpMethod.valueOf(value[0]);
        final ProtocolAndVersion protocolAndVersion = new ProtocolAndVersion(value[2]);
        final String path = pathAndQuerystring[0];

        String queryStringStr = "";
        if (pathAndQuerystring.length == 2) {
            queryStringStr = pathAndQuerystring[1];
        }

        final Parameters queryString = new Parameters(queryStringStr);

        return new RequestLine(httpMethod, path, queryString, protocolAndVersion);
    }
}
