package webserver;

import http.QueryString;
import http.QueryStringParser;
import http.RequestLine;
import http.RequestLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

            int contentLength = Integer.parseInt(Objects.toString(requestHeaders.get("Content-Length"), "0"));
            if (contentLength > 0) {
                String queryStringData = IOUtils.readData(reader, contentLength);
                QueryString queryString = QueryStringParser.parse(queryStringData);
                if (queryStringData != null) {
                    parameters = queryString.getParameters();
                }
            }

            return new HttpRequest(requestLine, requestHeaders, parameters);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
