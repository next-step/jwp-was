package webserver;

import http.QueryString;
import http.QueryStringParser;
import http.RequestLine;
import http.RequestLineParser;
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
            Map<String, String> requestHeader = new HashMap<>();
            Map<String, String> parameters = new HashMap<>();

            String sep = ": ";
            String line = reader.readLine();
            String prevLine = null;

            while (line != null && "".equals(line)) {
                if (line.contains(sep)) {
                    String[] split = line.split(sep);
                    requestHeader.put(split[0], split[1]);
                }

                line = reader.readLine();
            }

            if (line != null) {
                line = reader.readLine();

                QueryString queryString = QueryStringParser.parse(line);
                if (queryString != null) {
                    parameters = queryString.getParameters();
                }
            }

            return new HttpRequest(requestLine, requestHeader, parameters);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
