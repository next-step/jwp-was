package webserver.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static webserver.request.RequestHeaders.*;

/**
 * Created by hspark on 2019-08-05.
 */
public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);

    public static HttpRequest create(InputStream in) throws IOException {
        HttpRequest.HttpRequestBuilder builder = HttpRequest.builder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        buildRequestLine(builder, reader);
        RequestHeaders requestHeaders = buildRequestHeader(builder, reader);

        if (requestHeaders.hasContentLength()) {
            int contentLength = Integer.parseInt(requestHeaders.getHeader(CONTENT_LENGTH));
            buildRequestBody(builder, reader, contentLength);
        }

        return builder.build();
    }

    private static String buildRequestLine(HttpRequest.HttpRequestBuilder builder, BufferedReader reader) throws IOException {
        String line = reader.readLine();
        logger.debug("Request Line : {}", line);
        RequestLine requestLine = RequestLine.parse(line);
        builder.requestLine(requestLine);
        return line;
    }

    private static void buildRequestBody(HttpRequest.HttpRequestBuilder builder, BufferedReader reader, int contentLength) throws IOException {
        String body = IOUtils.readData(reader, contentLength);
        logger.debug("Request Body : {}", body);
        builder.requestBody(body);
    }

    private static RequestHeaders buildRequestHeader(HttpRequest.HttpRequestBuilder builder, BufferedReader reader) throws IOException {
        RequestHeaders requestHeaders = new RequestHeaders();
        String line = reader.readLine();
        while (!StringUtils.EMPTY.equals(line)) {
            logger.debug("RequestHeaders : {}", line);
            requestHeaders.add(line);
            line = reader.readLine();
        }
        builder.requestHeaders(requestHeaders);
        return requestHeaders;
    }
}
