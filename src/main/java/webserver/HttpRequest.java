package webserver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeaders requestHeaders;

    public static HttpRequest of(BufferedReader br) throws IOException {
        String requestLineText = parseRequestLine(br);
        RequestLine requestLine = RequestLine.of(requestLineText);

        List<String> requestHeaderTexts = parseRequestHeader(br);
        RequestHeaders requestHeaders = RequestHeaders.of(requestHeaderTexts);

        return new HttpRequest(requestLine, requestHeaders);
    }

    private static String parseRequestLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static List<String> parseRequestHeader(BufferedReader br) throws IOException {
        List<String> requestHeaderTexts = new ArrayList<>();
        String line;
        do {
            line = br.readLine();
            if (StringUtils.isNull(line)) {
                break;
            }
            requestHeaderTexts.add(line);
        } while (!"".equals(line));
        return requestHeaderTexts;
    }
}
