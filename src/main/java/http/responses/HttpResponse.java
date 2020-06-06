package http.responses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TemplateReader;
import utils.TemplateRenderer;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
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

    private final DataOutputStream dos;
    private final Map<String, String> responseHeaders = new HashMap<>();
    private HttpStatus status;

    public HttpResponse(OutputStream dos) {
        this.dos = new DataOutputStream(dos);
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
        renderResponseContext(dos, context);
    }

    public void render(String path) {
        final byte[] rawBody = convertFileToByte(path);
        final ResponseContext.ResponseContextBuilder builder = ResponseContext.builder();
        responseHeaders.forEach(builder::addHeader);
        final HttpStatus httpStatus = Optional.ofNullable(status).orElse(HttpStatus.OK);
        final ResponseContext context = builder
                .status(httpStatus)
                .addHeader("Content-Length", String.valueOf(rawBody.length))
                .body(rawBody)
                .build();
        renderResponseContext(dos, context);
    }

    public void render(String path, Object model) {
        final String rendered = TemplateRenderer.render(path, model);
        final ResponseContext.ResponseContextBuilder builder = ResponseContext.builder();
        responseHeaders.forEach(builder::addHeader);
        final HttpStatus httpStatus = Optional.ofNullable(status).orElse(HttpStatus.OK);
        final ResponseContext context = builder
                .status(httpStatus)
                .addHeader("Content-Type", "text/html;charset=utf-8")
                .body(rendered.getBytes())
                .build();
        renderResponseContext(dos, context);
    }

    private void renderResponseContext(DataOutputStream dos, ResponseContext context) {
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

    private static byte[] convertFileToByte(String path) {
        try {
            return TemplateReader.read(path);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
