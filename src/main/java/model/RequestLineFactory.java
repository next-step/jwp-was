package model;

import exception.HttpMethodExceptionMessage;
import exception.NotFoundHttpMethodException;

import static utils.DelimiterConstants.SLASH;
import static utils.DelimiterConstants.SPACE;

public class RequestLineFactory {

    public static RequestLine parsing(String firstLine) {
        String[] requestDataArray = firstLine.split(SPACE);
        HttpMethod method = HttpMethod.of(requestDataArray[0])
                .orElseThrow(() -> new NotFoundHttpMethodException(HttpMethodExceptionMessage.NOT_FOUND_MESSAGE));
        String path = requestDataArray[1];
        String[] protocolAndVersion = requestDataArray[2].split(SLASH);
        if (HttpMethod.GET.equals(method)) {
            return RequestLine.createGetRequest(method, path, protocolAndVersion);
        }
        return RequestLine.createPostRequest(method, path, protocolAndVersion);
    }
}
