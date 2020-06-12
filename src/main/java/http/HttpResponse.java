package http;


/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpResponse {

    private static final String HTML_CONTENT_TYPE = "text/html;charset=utf-8";
    private static final ProtocolVersion protocolVersion = new ProtocolVersion("HTTP/1.1");
    private static final String HEADER_DELIMITER = " ";
    private static final String CRLF = "\r\n";
    private final HttpHeaders headers = new HttpHeaders();
    private final Cookies cookies = new Cookies();
    private HttpStatusCode statusCode;
    private byte[] responseBody;

    public void redirect(String redirectUrl) {
        statusCode = HttpStatusCode.FOUND;
        headers.addHeader(HttpHeaderNames.LOCATION.toString(), redirectUrl);
        responseBody = new byte[0];
    }

    public void responseHTML(byte[] body) {
        statusCode = HttpStatusCode.OK;
        headers.addHeader(HttpHeaderNames.CONTENT_TYPE.toString(), HTML_CONTENT_TYPE);
        headers.addHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), String.valueOf(body.length));
        responseBody = body;
    }

    public void responseStatic(byte[] body, String contentType) {
        statusCode = HttpStatusCode.OK;
        headers.addHeader(HttpHeaderNames.CONTENT_TYPE.toString(), contentType);
        headers.addHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), String.valueOf(body.length));
        responseBody = body;
    }

    public void addCookie(String key, String value) {
        cookies.addCookie(key, value);
    }

    public void addCookiePath(String path) {
        cookies.addCookiePath(path);
    }

    public String getHeaderString() {
        StringBuffer sb = new StringBuffer();
        buildStatusLineString(sb);
        buildHeadersString(sb);
        sb.append(CRLF);
        return sb.toString();
    }

    private void buildHeadersString(StringBuffer sb) {
        sb.append(headers.toString());
        if (!cookies.isEmpty()) {
            sb.append(cookies.toString());
        }
    }

    private void buildStatusLineString(StringBuffer sb) {
        sb.append(protocolVersion.toString())
            .append(HEADER_DELIMITER)
            .append(statusCode.getCode())
            .append(HEADER_DELIMITER)
            .append(statusCode.getReason())
            .append(CRLF);
    }

    public byte[] getBody() {
        return this.responseBody;
    }
}
