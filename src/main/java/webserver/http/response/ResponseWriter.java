package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ContentType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static model.Constant.LOCATION;
import static model.Constant.SET_COOKIE;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    public static final String LINE_SEPARATOR = "\r\n";
    public static final String HEADER_SEPARATOR = " ";
    public final static String EXTENSION_SEPARATOR = ".";


    private final DataOutputStream out;

    public ResponseWriter(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    public void process(HttpResponse httpResponse) {
        try {
            isExistResponseBody(httpResponse);
            isExistCookie(httpResponse);

            writeResponseLine(httpResponse.getResponseLine());
            writeResponseHeader(httpResponse.getResponseHeader());
            writeResponseBody(httpResponse.getResponseBody());
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void isExistCookie(HttpResponse httpResponse) {
        if (!httpResponse.isExistCookie()) {
            httpResponse.setHeader(SET_COOKIE, httpResponse.getCookie(SET_COOKIE));
        }
    }

    private void isExistResponseBody(HttpResponse httpResponse) {
        if (httpResponse.getContentLength() > 0) {
            httpResponse.setHeader(CONTENT_LENGTH, httpResponse.getContentLength());
        }
    }

    private void writeResponseBody(ResponseBody responseBody) throws IOException {
        out.writeBytes(LINE_SEPARATOR);
        out.write(responseBody.getResponseBody(), 0, responseBody.getContentLength());
    }

    private void writeResponseHeader(ResponseHeader responseHeader) throws IOException {
        ContentType contentType = getContentType((String) responseHeader.getHeader(LOCATION));
        for (Map.Entry<String, Object> entry : responseHeader.getHeaders().entrySet()) {
            out.writeBytes(entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + LINE_SEPARATOR);
            logger.debug("responseHeader : {}", entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + LINE_SEPARATOR);
        }
    }

    private ContentType getContentType(String value) {
        if (value.contains(EXTENSION_SEPARATOR)) {
            return ContentType.getContentTypeFromExtension(value.substring(value.lastIndexOf(EXTENSION_SEPARATOR) + 1, value.length()));
        }
        return ContentType.HTML;
    }

    private void writeResponseLine(ResponseLine responseLine) throws IOException {
        out.writeBytes(responseLine.getProtocolAndVersion() + HEADER_SEPARATOR + getStatus(responseLine) + LINE_SEPARATOR);
    }

    private String getStatus(ResponseLine responseLine) {
        return responseLine.getHttpStatus().getCode() + HEADER_SEPARATOR + responseLine.getHttpStatus().getMessage();
    }

}
