package webserver.http.response.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.common.header.Header;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.header.Cookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public abstract class AbstractViewRenderer implements ViewRenderer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractViewRenderer.class);
    private static final String SP = " ";
    private static final String CRLF = "\r\n";
    private static final String RESPONSE_HEADER_DELIMITER = ": ";
    protected byte[] EMPTY_BODY = new byte[0];

    @Override
    public void render(ModelAndView modelAndView, HttpResponse httpResponse) {

        byte[] messageBody = EMPTY_BODY;
        try {

            messageBody = createResponseInfo(modelAndView, httpResponse);

            if (messageBody.length > 0) {
                httpResponse.addHeader(Header.CONTENT_LENGTH.getName(), String.valueOf(messageBody.length));
            }

            writeStream(messageBody, httpResponse);

        } catch (IOException e) {
            logger.error("body length : {}", messageBody.length, e);
            writeErrorPage(e, httpResponse);
        } catch (URISyntaxException e) {
            logger.error("요청 자원을 찾을 수 없음 : {}", modelAndView.getViewName(), e);
            writeErrorPage(e, httpResponse);
        }
    }

    void writeStream(byte[] messageBody, HttpResponse httpResponse) throws IOException {

        DataOutputStream outputStream = httpResponse.getOutputStream();

        // header
        outputStream.writeBytes(createStatusLine(httpResponse));
        outputStream.writeBytes(createHeaders(httpResponse));
        outputStream.writeBytes(CRLF);

        // body
        if (messageBody.length > 0) {
            outputStream.write(messageBody, 0, messageBody.length);
        }
    }

    private String createStatusLine(HttpResponse httpResponse) {
        String version = httpResponse.getHttpVersion();
        HttpStatus status = httpResponse.getHttpStatus();

        // Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
        return String.join(SP, version, String.valueOf(status.getCode()), status.getReasonPhrase()) + CRLF;
    }

    private String createHeaders(HttpResponse response) {
        Map<String, String> headers = response.getHeaders();
        List<Cookie> cookies = response.getCookies();
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> header : headers.entrySet()) {
            appendHeaderLine(builder, header.getKey(), header.getValue());
        }

        for (Cookie cookie : cookies) {
            appendHeaderLine(builder, Header.SET_COOKIE.getName(), cookie.toString());
        }
        return builder.toString();
    }

    private void appendHeaderLine(StringBuilder builder, String key, String value) {
        builder.append(key).append(RESPONSE_HEADER_DELIMITER)
                .append(value).append(CRLF);
    }

    void writeErrorPage(Exception e, HttpResponse httpResponse) {
        writeErrorPage(e, httpResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    void writeErrorPage(Exception e, HttpResponse httpResponse, HttpStatus httpStatus) {
        DataOutputStream outputStream = httpResponse.getOutputStream();
        httpResponse.setHttpStatus(httpStatus);
        try {
            outputStream.writeBytes(createStatusLine(httpResponse));
            outputStream.writeBytes(createHeaders(httpResponse));
            outputStream.writeBytes(CRLF);
        } catch (IOException ex) {
            logger.error("error page rendering exception", e);
        }
    }

    protected abstract byte[] createResponseInfo(ModelAndView modelAndView, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
