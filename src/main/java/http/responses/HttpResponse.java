package http.responses;

import http.requests.HttpRequest;
import http.view.TemplateViewResolver;
import http.view.View;
import http.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @see <a href=https://tools.ietf.org/html/rfc2616#section-6>Response Specification</a>
 * 요약:
 * HTTP버전 상태코드 설명구문
 * 응답헤더(요청 헤더처럼)
 */
public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private final HttpRequest request;
    private final DataOutputStream dos;
    private final Map<String, String> responseHeaders = new HashMap<>();
    private HttpStatus status;

    private final Map<String, Object> attribute = new HashMap<>();

    public HttpResponse(HttpRequest request, OutputStream dos) {
        this.request = request;
        this.dos = new DataOutputStream(dos);
    }

    public void addAttribute(String key, Object value) {
        attribute.put(key, value);
    }

    public Map<String, Object> getAttribute() {
        return attribute;
    }

    public void addHeader(String key, String value) {
        this.responseHeaders.put(key, value);
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void sendRedirect(String uri) {
        final ResponseContext.ResponseContextBuilder builder = ResponseContext.builder();
        responseHeaders.forEach(builder::addHeader);
        final ResponseContext context = builder.status(HttpStatus.FOUND).addHeader("Location", uri).build();
        renderResponseContext(context);
    }

    public void renderTemplate(String path) {
        final ViewResolver resolver = new TemplateViewResolver();
        final View view = resolver.resolve(path);
        try {
            view.render(request, this);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response500Error();
        }
    }

    public void responseNotFound() {
        renderResponseContext(ResponseContext.of(HttpStatus.NOT_FOUND));
    }

    public void response500Error() {
        renderResponseContext(ResponseContext.of(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public void responseBody(byte[] body) {
        final ResponseContext.ResponseContextBuilder builder = ResponseContext.builder();
        responseHeaders.forEach(builder::addHeader);
        final HttpStatus httpStatus = Optional.ofNullable(status).orElse(HttpStatus.OK);
        final ResponseContext context = builder
                .status(httpStatus)
                .addHeader("Content-Length", String.valueOf(body.length))
                .body(body)
                .build();
        renderResponseContext(context);
    }

    private void renderResponseContext(ResponseContext context) {
        try {
            dos.writeBytes(context.getStatusLine());
            for (String header : context.getResponseHeaderList()) {
                dos.writeBytes(header);
            }
            dos.writeBytes("\r\n");
            final byte[] responseBody = context.getResponseBody();
            if (responseBody != null) {
                dos.write(responseBody, 0, responseBody.length);
                dos.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
