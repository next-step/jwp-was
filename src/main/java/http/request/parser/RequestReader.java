package http.request.parser;

import http.common.Cookies;
import http.request.RequestHeader;
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

public class RequestReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);

    public static HttpRequest read(InputStream in) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String line = br.readLine();
        logger.debug("Request Line :: {}", line);
        RequestLine requestLine = RequestLineParser.parse(line);

        StringBuffer sb = new StringBuffer();
        while (!Strings.isEmpty(line)) {
            line = br.readLine();
            logger.debug("Header :: {}", line);
            sb.append(line).append("\n");
        }
        RequestHeader header = RequestHeaderParser.parse(sb.toString());

        String cookieValues = header.getValue("Cookie");
        Cookies cookies = new Cookies(cookieValues);

        String contentLengthStr = header.getValue("Content-Length");
        int contentLength = 0;
        if (!Strings.isBlank(contentLengthStr)) {
            contentLength = Integer.parseInt(contentLengthStr);
        }

        String requestBody = IOUtils.readData(br, contentLength);
        logger.debug("Body :: {}", requestBody);

        return new HttpRequest(requestLine, header, cookies, requestBody);
    }

}
