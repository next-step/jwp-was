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

        builder.requestLine(createRequestLine(reader));

        RequestHeaders requestHeaders = createdRequestHeaders(reader);
        builder.requestHeaders(requestHeaders);

        if (requestHeaders.hasContentLength()) {
            int contentLength = Integer.parseInt(requestHeaders.getHeader(CONTENT_LENGTH));
            builder.requestBody(createRequestBody(reader, contentLength));
        }

        return builder.build();
    }

    private static RequestLine createRequestLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        logger.debug("Request Line : {}", line);
        return RequestLine.parse(line);
    }

    private static String createRequestBody(BufferedReader reader, int contentLength) throws IOException {
        String body = IOUtils.readData(reader, contentLength);
        logger.debug("Request Body : {}", body);
        return body;
    }

    private static RequestHeaders createdRequestHeaders(BufferedReader reader) throws IOException {
        RequestHeaders requestHeaders = new RequestHeaders();
        String line = reader.readLine();
        while (!StringUtils.EMPTY.equals(line)) {
            logger.debug("RequestHeaders : {}", line);
            requestHeaders.add(line);
            line = reader.readLine();
        }
        return requestHeaders;
    }
}
