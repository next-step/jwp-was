package webserver.http.request;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 5 Request
 *
 * A request message from a client to a server includes, within the
 * first line of that message, the method to be applied to the resource,
 * the identifier of the resource, and the protocol version in use.
 *
 *      Request       = Request-Line              ; Section 5.1
 *                      *(( general-header        ; Section 4.5
 *                       | request-header         ; Section 5.3
 *                       | entity-header ) CRLF)  ; Section 7.1
 *                      CRLF
 *                      [ message-body ]          ; Section 4.3
 *
 * https://tools.ietf.org/html/rfc2616#section-5
 */
@Getter
public class Request {

    private RequestLine requestLine;
    private List<RequestHeaderField> requestHeaderFields;

    private MessageBody messageBody;

    public Request(final List<String> httpRequestHeaders) {
        if (CollectionUtils.isEmpty(httpRequestHeaders)) {
            return;
        }

        final String requestLine = httpRequestHeaders.get(RequestLine.INDEX_OF_REQUEST_LINE);
        this.requestLine = RequestLine.parse(requestLine);
    }

    public String path() {
        return requestLine.path();
    }

    public Query query() {
        return requestLine.query();
    }
}
