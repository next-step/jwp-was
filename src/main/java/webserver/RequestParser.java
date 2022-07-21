package webserver;

import webserver.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class RequestParser {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String URL_DELIMITER = "\\?";
    private static final String QUERY_PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PROTOCOL_DELIMITER = "/";

    private static final int NO_QUERY_PARAMETER = 1;

    public static RequestLine parseRequestLine(final String startLine){
        String[] httpElement = startLine.split(REQUEST_LINE_DELIMITER);

        HttpMethod httpMethod = HttpMethod.from(httpElement[RequestLine.METHOD_INDEX]);
        Url url = parseUrl(httpElement[RequestLine.URL_INDEX]);
        ProtocolVersion protocol = parseProtocolVersion(httpElement[RequestLine.PROTOCOL_INDEX]);

        return new RequestLine(httpMethod, url, protocol);
    }

    public static Url parseUrl(final String url) {
        String[] splitUrl = url.split(URL_DELIMITER);

        return new Url(
                splitUrl[Url.PATH_INDEX],
                parseQueryParameter(splitUrl)
        );
    }

    public static QueryParameter parseQueryParameter(final String[] splitUrl) {
        if (splitUrl.length == NO_QUERY_PARAMETER) {
            return new QueryParameter(Collections.EMPTY_MAP);
        }

        String[] splitParameters = splitUrl[Url.QUERY_PARAMETER_INDEX].split(QUERY_PARAMETER_DELIMITER);

        return new QueryParameter(
                Arrays.stream(splitParameters)
                        .map(value -> value.split(KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]))
        );
    }

    private static ProtocolVersion parseProtocolVersion(final String protocolVersion){
        String[] splitProtocolVersion = protocolVersion.split(PROTOCOL_DELIMITER);

        return new ProtocolVersion(
                Protocol.valueOf(splitProtocolVersion[ProtocolVersion.PROTOCOL_INDEX]),
                HttpVersion.from(splitProtocolVersion[ProtocolVersion.VERSION_INDEX])
        );
    }
}
