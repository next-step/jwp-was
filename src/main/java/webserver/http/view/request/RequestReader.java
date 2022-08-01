package webserver.http.view.request;

import webserver.http.domain.Cookies;
import webserver.http.domain.Headers;
import webserver.http.domain.exception.NullRequestException;
import webserver.http.domain.request.Parameters;
import webserver.http.domain.request.Request;
import webserver.http.domain.request.RequestLine;
import webserver.http.view.CookiesParser;
import webserver.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RequestReader {
    private final CookiesParser cookiesParser;

    public RequestReader(CookiesParser cookiesParser) {
        this.cookiesParser = cookiesParser;
    }

    public Request read(BufferedReader bufferedReader) throws IOException {
        String requestLineMessage = bufferedReader.readLine();
        if (Objects.isNull(requestLineMessage)) {
            throw new NullRequestException("요청값이 null로 들어왔습니다. 해당 요청은 무시합니다.");
        }
        List<String> headerMessages = IOUtils.readWhileEmptyLine(bufferedReader);
        headerMessages.forEach(System.out::println);

        RequestLine requestLine = RequestLine.from(requestLineMessage);
        Headers requestHeaders = Headers.from(headerMessages);
        Request request = parseRequestExcludeBody(requestLine, requestHeaders);

        if (request.hasContents() && request.hasContentType("application/x-www-form-urlencoded")) {
            String requestBody = IOUtils.readData(bufferedReader, request.getContentLength());
            Parameters parameters = Parameters.from(requestBody);
            request.addParameters(parameters);
        }

        return request;
    }

    private Request parseRequestExcludeBody(RequestLine requestLine, Headers requestHeaders) {
        if (requestHeaders.contains("Cookie")) {
            String cookieMessage = requestHeaders.getValue("Cookie");
            Cookies cookies = cookiesParser.parse(cookieMessage);
            return new Request(requestLine, requestHeaders, cookies);
        }
        return new Request(requestLine, requestHeaders);
    }
}
