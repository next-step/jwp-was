package webserver;

import http.QueryString;
import http.QueryStringParser;
import http.RequestLine;
import http.RequestLineParser;
import http.request.HttpRequest;
import http.request.HttpRequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestReader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);
    private static final String SEPARATOR = ": ";

    public static HttpRequest read(InputStream in) {

        try {
            BufferedReader br = HttpRequestReader.readBuffer(in);

            RequestLine requestLine = HttpRequestReader.parseReadLine(br);

            Map<String, String> headerMap = HttpRequestReader.readHeader(br);

            HttpRequestHeader header = new HttpRequestHeader(headerMap);

            Map<String, String> paramMap = new HashMap<>();
            if (header.hasBody()) {
                int contentLength = header.getContentLength();
                paramMap = HttpRequestReader.readBody(br, contentLength);
            }

            return new HttpRequest(requestLine, header, paramMap);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public static BufferedReader readBuffer(InputStream in) throws Exception {
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        return new BufferedReader(isr);
    }

    public static RequestLine parseReadLine(BufferedReader br) throws Exception {
        String requestLineString = br.readLine();
        return RequestLineParser.parse(requestLineString);
    }

    public static Map<String, String> readHeader(BufferedReader br) throws Exception {
        Map<String, String> requestHeaders = new HashMap<>();

        String line = br.readLine();
        while (!"".equals(line) && line != null) {
            if (line.contains(SEPARATOR)) {
                String[] split = line.split(SEPARATOR);
                requestHeaders.put(split[0], split[1]);
            }

            line = br.readLine();
        }
        return requestHeaders;
    }

    public static Map<String, String> readBody(BufferedReader br, int contentLength) throws Exception {
        Map<String, String> parameters = new HashMap<>();

        String queryStringData = IOUtils.readData(br, contentLength);
        QueryString queryString = QueryStringParser.parse(queryStringData);
        if (queryStringData != null) {
            parameters = queryString.getParameters();
        }

        return parameters;
    }


}
