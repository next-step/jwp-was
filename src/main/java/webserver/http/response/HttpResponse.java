package webserver.http.response;

import java.io.DataOutputStream;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class HttpResponse {


    private static final String CRLF = "\r\n";
    private static final String RESPONSE_HEADER_DELIMITER = ": ";
    private String statusLine;
    private Map<String, String> headers;
    private String messageBody;

    private DataOutputStream outputStream;

    public HttpResponse(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
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

    public String getStatusLine() {
        return statusLine;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }
}
