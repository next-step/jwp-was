package http.request.parser;

import http.common.ProtocolAndVersion;
import http.request.HttpMethod;
import http.request.Parameters;
import http.request.RequestLine;

import java.util.Optional;

public class RequestLineParser {

    private static final String REQUEST_LINE_TOKENIZER = " ";
    private static final String QUERYSTRING_START_CHARACTER = "\\?";

    public static RequestLine parse(String requestLineStr) {
        final String[] value = requestLineStr.split(REQUEST_LINE_TOKENIZER);
        final String[] pathAndQuerystring = value[1].split(QUERYSTRING_START_CHARACTER);
        final HttpMethod httpMethod = HttpMethod.valueOf(value[0]);
        final ProtocolAndVersion protocolAndVersion = new ProtocolAndVersion(value[2]);
        final String path = pathAndQuerystring[0];

        Parameters queryString = new Parameters();
        if (pathAndQuerystring.length == 2) {
            queryString = new Parameters(pathAndQuerystring[1]);
        }

        return new RequestLine(httpMethod, path, queryString, protocolAndVersion);
    }
}
