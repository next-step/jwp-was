package webserver;

import http.Headers;
import http.parser.RequestHeaderParser;
import http.parser.RequestLineParser;
import http.request.HttpRequest;
import http.request.RequestLine;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequestReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);

    public static HttpRequest read(InputStream in) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String requestLineStr = br.readLine();
        logger.debug("Request Line :: {}", requestLineStr);
        RequestLine requestLine = RequestLineParser.parse(requestLineStr);

        List<String> headerList = new ArrayList<>();
        String headerStr = br.readLine();
        while (!headerStr.equals("")) {
            logger.debug("Header :: {}", headerStr);
            headerList.add(headerStr);
            headerStr = br.readLine();
        }
        Headers headers = RequestHeaderParser.parse(headerList);

        String contentLengthStr = headers.getValue("Content-Length");
        int contentLength = 0;
        if (!Strings.isBlank(contentLengthStr)) {
            contentLength = Integer.parseInt(contentLengthStr);
        }

        String requestBody = IOUtils.readData(br, contentLength);
        logger.debug("Body :: {}", requestBody);

        return new HttpRequest(requestLine, headers, requestBody);
    }

}
