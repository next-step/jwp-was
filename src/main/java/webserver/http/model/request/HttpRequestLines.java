package webserver.http.model.request;

import exception.IllegalHttpRequestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestLines {
    private final List<String> lines;

    public HttpRequestLines(String textLines) {
        if (textLines == null || textLines.length() < 1) {
            throw new IllegalHttpRequestException("httpRequest는 빈 값이 전달 될 수 없습니다.");
        }
        lines = Arrays.asList(textLines.split("\n"));
        if (lines.size() < 2) {
            throw new IllegalHttpRequestException("httpRequest는 적어도 두 줄 이상의 정보로 구성되어 있습니다.");
        }
    }

    public String requestLine() {
        return lines.get(0);
    }

    public List<String> requestHeader() {
        return requestHeaderAndBody().get(0).stream().filter(header -> !"".equals(header)).collect(Collectors.toList());
    }

    public List<String> requestBody() {
        return requestHeaderAndBody().get(1);
    }

    private List<List<String>> requestHeaderAndBody() {
        return new ArrayList<>(lines.stream().filter(text -> !text.equals(lines.get(0)))
                .collect(Collectors.partitioningBy(line -> lines.indexOf(line) > lines.indexOf(""))).values());
    }
}
