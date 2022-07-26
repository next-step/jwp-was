package webserver.http.request;

import utils.IOUtils;
import webserver.http.request.exception.NullRequestException;
import webserver.http.request.parser.HeadersParser;
import webserver.http.request.parser.ParametersParser;
import webserver.http.request.parser.RequestLineParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RequestReader {
    private final RequestLineParser requestLineParser;
    private final HeadersParser headersParser;
    private final ParametersParser requestBodyParser;

    public RequestReader(RequestLineParser requestLineParser, HeadersParser headersParser, ParametersParser requestBodyParser) {
        this.requestLineParser = requestLineParser;
        this.headersParser = headersParser;
        this.requestBodyParser = requestBodyParser;
    }

    public Request read(BufferedReader bufferedReader) throws IOException {
        String requestLineMessage = bufferedReader.readLine();
        if (Objects.isNull(requestLineMessage)) {
            throw new NullRequestException("요청값이 null로 들어왔습니다. 해당 요청은 무시합니다.");
        }
        List<String> headerMessages = IOUtils.readWhileEmptyLine(bufferedReader);

        RequestLine requestLine = requestLineParser.parse(requestLineMessage);
        Headers requestHeaders = headersParser.parse(headerMessages);
        Request request = new Request(requestLine, requestHeaders);

        if (request.hasContents()) {
            String requestBody = IOUtils.readData(bufferedReader, request.getContentLength());
            Parameters parameters = requestBodyParser.parse(requestBody);
            request.addBody(parameters);
        }

        return request;
    }
}
