package http.request.parser;

import http.common.ContentType;
import http.common.Cookies;
import http.common.HeaderFieldName;
import http.request.HttpRequest;
import http.request.Parameters;
import http.request.RequestHeader;
import http.request.RequestLine;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.session.HttpSession;
import webserver.session.SessionStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);
    private static final String INPUT_STREAM_DECODER = "UTF-8";
    private static final String SESSION_ID = "JSESSIONID";

    public static HttpRequest read(InputStream in) throws IOException {

        final BufferedReader br = new BufferedReader(new InputStreamReader(in, INPUT_STREAM_DECODER));

        String line = br.readLine();
        logger.debug("Request Line :: {}", line);
        final RequestLine requestLine = RequestLineParser.parse(line);

        final StringBuffer sb = new StringBuffer();
        while (!Strings.isEmpty(line)) {
            line = br.readLine();
            logger.debug("Header :: {}", line);
            sb.append(line).append("\n");
        }
        final RequestHeader header = RequestHeaderParser.parse(sb.toString());

        final Cookies cookies = header.getValue(HeaderFieldName.COOKIE)
                .map(Cookies::new)
                .orElseGet(Cookies::new);

        final int contentLength = header.getValue(HeaderFieldName.CONTENT_LENGTH)
                .map(Integer::valueOf)
                .orElse(0);

        final String requestBody = IOUtils.readData(br, contentLength);
        logger.debug("Body :: {}", requestBody);

        final String sessionId = cookies.getValue(SESSION_ID);
        final HttpSession session = SessionStore.get(sessionId);

        Parameters formData = header.getValue(HeaderFieldName.CONTENT_TYPE)
                .filter(ContentType.APPLICATION_X_WWW_FORM_UNLENCODED.getValue()::equals)
                .map(x -> new Parameters(requestBody))
                .orElseGet(Parameters::new);

        return new HttpRequest(requestLine, header, formData, cookies, requestBody, session);
    }

}
