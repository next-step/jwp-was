package webserver.http.response;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class HttpResponse {

    private static final String SP = " ";
    public static final String CRLF = "\r\n";
    private static final String RESPONSE_HEADER_DELIMITER = ": ";
    private HttpStatus httpStatus;
    private Map<String, String> headers;
    private String messageBody;

    private DataOutputStream outputStream;

    public HttpResponse(DataOutputStream outputStream) {
        headers = new HashMap<>();
        this.outputStream = outputStream;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeaders() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder
                    .append(header.getKey())
                    .append(RESPONSE_HEADER_DELIMITER)
                    .append(header.getValue())
                    .append(CRLF);
        }
        return builder.toString();
    }

    // Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
    public String getStatusLine() {
        return "HTTP/1.1" + SP + getHttpStatus().getCode() + SP + getHttpStatus().getReasonPhrase() + CRLF;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

}
