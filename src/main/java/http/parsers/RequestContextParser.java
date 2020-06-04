package http.parsers;

import http.requests.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RequestContextParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestContextParser.class);

    public static RequestContext parse(InputStream input) {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            final String rawRequestLine = br.readLine();
            final ArrayList<String> rawRequestHeaders = new ArrayList<>();

            logger.debug("request line: {}", rawRequestLine);
            String readLine;
            int contentLength = 0;
            do {
                readLine = br.readLine();
                if (!readLine.equals("")) {
                    // request body의 경계이므로
                    rawRequestHeaders.add(readLine);
                }

                // TODO: 엌ㅋㅋㅋㅋㅋㅋ 내가 하드코딩이라니.. Content-Type에 맞는 body parser를 따로 두는 방향으로 가면 sophisticated 할 듯..
                if (readLine.toLowerCase().contains("content-length")) {
                    final String[] contentLengthHeader = readLine.split(":");
                    contentLength = Integer.parseInt(contentLengthHeader[1].trim());
                }

                logger.debug("header: {}", readLine);
            } while (readLine != null && !readLine.equals(""));
            final String parsedBody = IOUtils.readData(br, contentLength);
            logger.debug("parsed body: {}", parsedBody);
            // TODO: body가 string이라는 법은 없지만 지금은 string으로 하자 'ㅅ'
            return new RequestContext(rawRequestLine, rawRequestHeaders, parsedBody);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
