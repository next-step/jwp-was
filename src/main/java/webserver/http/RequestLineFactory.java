package webserver.http;

import webserver.http.exception.HttpMethodExceptionMessage;
import webserver.http.exception.NotFoundHttpMethodException;

import static utils.DelimiterConstants.SLASH;
import static utils.DelimiterConstants.SPACE;

public class RequestLineFactory {

    public static RequestLine parsing(String firstLine) {
        String[] requestDataArray = firstLine.split(SPACE);
        HttpMethod method = HttpMethod.of(requestDataArray[0])
                .orElseThrow(() -> new NotFoundHttpMethodException(HttpMethodExceptionMessage.NOT_FOUND_MESSAGE));
        Path path = Path.of(requestDataArray[1]);
        String[] protocolAndVersion = requestDataArray[2].split(SLASH);
        return RequestLine.of(method, path, protocolAndVersion);
    }
}
