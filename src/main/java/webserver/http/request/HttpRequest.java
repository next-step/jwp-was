package webserver.http.request;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 5 Request
 *
 * A request message from a client to a server includes, within the
 * first line of that message, the method to be applied to the resource,
 * the identifier of the resource, and the protocol version in use.
 *
 *      Request = Request-Line              ; Section 5.1
 *                *(( general-header        ; Section 4.5
 *                 | request-header         ; Section 5.3
 *                 | entity-header ) CRLF)  ; Section 7.1
 *                CRLF
 *                [ message-body ]          ; Section 4.3
 *
 * https://tools.ietf.org/html/rfc2616#section-5
 */
@Getter
public class HttpRequest {

    private RequestLine requestLine;
    private GeneralHeader generalHeader;
    private RequestHeader requestHeader;
    private EntityHeader entityHeader;
    private MessageBody messageBody;

    public HttpRequest(final BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        if (StringUtils.isBlank(requestLine)) {
            return;
        }
        setRequestLine(requestLine);

        List<String> httpRequestHeaders = readHttpRequestHeaders(bufferedReader);
        setGeneralHeader(httpRequestHeaders);
        setRequestHeader(httpRequestHeaders);
        setEntityHeader(httpRequestHeaders);

        final String messageBody = readMessageBody(bufferedReader);
        setMessageBody(messageBody);
    }

    public String path() {
        return requestLine.path();
    }

    public String removeExtensionPath() {
        final String DOT = ".";
        if (StringUtils.contains(path(), DOT)) {
            return StringUtils.substring(path(), 0, StringUtils.lastIndexOf(path(), DOT));
        }

        return path();
    }

    public Query query() {
        Query query = messageBody.getQuery();
        if (query.isEmpty()) {
            return requestLine.query();
        }

        return query;
    }

    private List<String> readHttpRequestHeaders(BufferedReader bufferedReader) throws IOException {
        String readLine;
        List<String> httpRequestHeaders = new ArrayList<>();

        do {
            readLine = bufferedReader.readLine();
            httpRequestHeaders.add(readLine);
            if (readLine == null || readLine.isEmpty()) {
                break;
            }
        } while (bufferedReader.ready());

        return httpRequestHeaders;
    }

    private String readMessageBody(BufferedReader bufferedReader) throws IOException {
        final String contentLength = entityHeader.get(EntityHeaderFields.CONTENT_LENGTH);

        if (StringUtils.isNumeric(contentLength)) {
            return IOUtils.readData(bufferedReader, Integer.valueOf(contentLength));
        }

        return "";
    }

    private void setRequestLine(final String requestLine) {
        this.requestLine = RequestLine.parse(requestLine);
    }

    private void setGeneralHeader(final List<String> httpRequestHeaders) {
        this.generalHeader = new GeneralHeader(httpRequestHeaders);
    }

    private void setRequestHeader(final List<String> httpRequestHeaders) {
        this.requestHeader = new RequestHeader(httpRequestHeaders);
    }

    private void setEntityHeader(final List<String> httpRequestHeaders) {
        this.entityHeader = new EntityHeader(httpRequestHeaders);
    }

    private void setMessageBody(final String messageBody) throws UnsupportedEncodingException {
        this.messageBody = new MessageBody(messageBody);
    }
}
