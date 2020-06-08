package webserver;

import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestReader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);

    public static HttpRequest read(InputStream in) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String requestLineString = reader.readLine();
            RequestLine requestLine = RequestLineParser.parse(requestLineString);
            Map<String, String> requestHeaders = new HashMap<>();
            Map<String, String> parameters = new HashMap<>();

            String sep = ": ";
            String line = reader.readLine();

            while (!"".equals(line) && line != null) {
                if (line.contains(sep)) {
                    String[] split = line.split(sep);
                    requestHeaders.put(split[0], split[1]);
                }

                line = reader.readLine();
            }

            if (HttpMethod.POST.equals(requestLine.getMethod())) {
                line = reader.readLine();
                if (line != null) {
                    QueryString queryString = QueryStringParser.parse(line);
                    if (queryString != null) {
                        parameters = queryString.getParameters();
                    }
                }
            }

            return new HttpRequest(requestLine, requestHeaders, parameters);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
