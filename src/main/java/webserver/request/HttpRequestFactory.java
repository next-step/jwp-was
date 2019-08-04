package webserver.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hspark on 2019-08-05.
 */
public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);

    public static HttpRequest create(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        logger.debug("Request Line : {}", line);
        HttpRequest.HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();
        httpRequestBuilder.requestLine(line);


        while (!StringUtils.EMPTY.equals(line)) {
            line = reader.readLine();
            if (StringUtils.EMPTY.equals(line)) {
                break;
            }
            httpRequestBuilder.addHeader(line);
            logger.debug("Headers : {}", line);
        }

        return httpRequestBuilder.build();
    }
}
