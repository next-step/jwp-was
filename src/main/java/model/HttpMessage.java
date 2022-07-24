package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HttpMessage {

    private static final Logger logger = LoggerFactory.getLogger(HttpMessage.class);

    private final String BODY_SEPARATOR = "";

    private final RequestLine requestLine;

    private RequestHeaders requestHeaders;

    private String body;

    public HttpMessage(List<String> httpMessageData) {
        if (!(httpMessageData instanceof ArrayList)) {
            httpMessageData = new ArrayList<>(httpMessageData);
        }

        if (this.isWrongFormat(httpMessageData)) {
            throw new IllegalArgumentException();
        }
        String requestLine = httpMessageData.get(0);
        this.requestLine = HttpParser.parseRequestLine(requestLine);

        if (httpMessageData.size() == 1) {
            return;
        }

        httpMessageData.remove(requestLine);

        if (!httpMessageData.contains(BODY_SEPARATOR)) {
            this.requestHeaders = new RequestHeaders(this.getRequestHeaders(httpMessageData, httpMessageData.size()));
            return;
        }

        if (httpMessageData.contains(BODY_SEPARATOR)) {
            int bodySeparatorIndex = httpMessageData.indexOf(BODY_SEPARATOR);
            this.requestHeaders = new RequestHeaders(this.getRequestHeaders(httpMessageData, bodySeparatorIndex));
            this.body = this.getBody(httpMessageData);
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public String getBody() {
        return body;
    }

    private boolean isWrongFormat(List<String> httpMessageData) {
        if (httpMessageData.isEmpty() || httpMessageData.stream().filter(data -> data.equals(BODY_SEPARATOR)).count() > 1) {
            return true;
        }

        return false;
    }

    private List<String> getRequestHeaders(List<String> httpMessageData, int bodySeparatorIndex) {
        return IntStream.range(0, bodySeparatorIndex)
                .mapToObj(index -> httpMessageData.remove(0))
                .collect(Collectors.toList());
    }

    private String getBody(List<String> httpMessageData) {
        String bodySeparator = httpMessageData.remove(0);
        if (!bodySeparator.equals(BODY_SEPARATOR)) {
            logger.error(this.toStringHttpMessageData(httpMessageData));
            throw new IllegalArgumentException();
        }

        if (httpMessageData.isEmpty()) {
            return null;
        }

        if (httpMessageData.size() != 1) {
            logger.error(this.toStringHttpMessageData(httpMessageData));
            throw new IllegalArgumentException();
        }

        return httpMessageData.get(0);
    }

    private String toStringHttpMessageData(List<String> httpMessageData) {
        StringBuilder value = new StringBuilder();
        value.append("[" + "\n");
        httpMessageData.forEach(data -> value.append(data).append("\n"));
        value.append("]");

        return value.toString();
    }

}
