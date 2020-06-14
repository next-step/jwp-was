package http;

import exception.WebServerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpRequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);

    private static final String CHARSET = "UTF-8";
    private static final String CREATE_REQUEST_FAIL = "Request 만드는 것에 실패했습니다.";

    public static HttpRequest createRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, CHARSET));

            final RequestLine requestLine = RequestLine.of(getFirstLine(br));
            final HttpHeaders headers = new HttpHeaders();
            readHeaders(br, headers);
            final QueryString queryString = getQueryStringFromRequestBody(br, headers);

            return new HttpRequest(requestLine, headers, queryString);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new WebServerException(CREATE_REQUEST_FAIL, e);
        }
    }

    private static QueryString getQueryStringFromRequestBody(BufferedReader br, HttpHeaders headers) throws IOException{
        int contentLength = parseInt(headers.getHeader(HttpHeaderNames.CONTENT_LENGTH.toString()));
        return QueryString.of(IOUtils.readData(br, contentLength));
    }

    private static int parseInt(String value) {
        return value == null ? 0 : Integer.parseInt(value);
    }

    private static void readHeaders(BufferedReader br, HttpHeaders headers) throws IOException {
        String line = br.readLine();
        while (!Strings.isEmpty(line)) {
            headers.addHeader(line);
            line = br.readLine();
        }
    }

    private static String getFirstLine(BufferedReader br) throws IOException {
        return Objects.toString(br.readLine());
    }
}
