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
        String line = reader.readLine();
        logger.debug("Request Line : {}", line);
        RequestLine requestLine = RequestLine.parse(line);
        builder.requestLine(requestLine);

        RequestHeaders requestHeaders = new RequestHeaders();
        while (!StringUtils.EMPTY.equals(line)) {
            line = reader.readLine();
            if (StringUtils.EMPTY.equals(line)) {
                break;
            }
            requestHeaders.add(line);
            logger.debug("RequestHeaders : {}", line);
        }
        builder.requestHeaders(requestHeaders);

        if (requestHeaders.hasContentLength()) {
            String body = IOUtils.readData(reader, Integer.parseInt(requestHeaders.getHeader(CONTENT_LENGTH)));
            logger.debug("Request Body : {}", body);
            RequestBody requestBody = RequestBody.parse(body);
            builder.requestBody(requestBody);
        }
        return builder.build();
    }
}
