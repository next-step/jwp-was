package webserver.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public static HttpRequest of(BufferedReader br) throws IOException {
        String requestLineText = parseRequestLine(br);
        log.info("RequestLine : {}", requestLineText);

        RequestLine requestLine = RequestLine.of(requestLineText);

        List<String> requestHeaderTexts = parseRequestHeader(br);
        RequestHeaders requestHeaders = RequestHeaders.of(requestHeaderTexts);

        RequestBody requestBody = null;
        if (requestLine.isPost()) {
            String data = IOUtils.readData(br, Integer.parseInt(requestHeaders.get("Content-Length")));
            requestBody = RequestBody.of(data);
        }

        return new HttpRequest(requestLine, requestHeaders, requestBody);
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

    public boolean isStaticFileRequest() {
        return requestLine.isFileTypeInUrl();
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public String getHost() {
        return requestLine.getUrl().split("\\?")[0];
    }
}
