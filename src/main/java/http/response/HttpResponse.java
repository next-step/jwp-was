package http.response;

import http.request.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static http.response.HttpStatusCode.FOUND;

@Slf4j
@RequiredArgsConstructor
public class HttpResponse {

    private final DataOutputStream dos;
    private final HttpRequest httpRequest;
    private final HttpResponseMetaData metaData;
    private TemplateRenderer templateRenderer = new TemplateRenderer();

    public static HttpResponse of(OutputStream outputStream, HttpRequest httpRequest) {
        DataOutputStream dos = new DataOutputStream(outputStream);
        return new HttpResponse(dos, httpRequest, new HttpResponseMetaData());
    }

    public void setModel(String key, Object value) {
        templateRenderer.setModel(key, value);
    }

    public void renderTemplate(String location) {
        try {
            String renderedPage = templateRenderer.render(location);
            updateResponseBodyContent(renderedPage.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void redirect(String location) {
        metaData.updateStatusCode(FOUND);
        metaData.putResponseHeader("Location", location);
    }

    public void setCookie(String content) {
        metaData.putResponseHeader("Set-Cookie", content);
    }

    public void setLoginCookie(boolean logined) {
        setCookie(String.format("logined=%s", logined));
    }

    public void setLoginCookie(boolean logined, String path) {
        if (path == null) {
            setLoginCookie(logined);
            return;
        }

        setCookie(String.format("logined=%s; Path=%s", logined, path));
    }

    public void updateResponseBodyContent(byte[] responseBody) {
        metaData.updateContentType(httpRequest.getMimeType(), responseBody.length);
        metaData.updateResponseBody(responseBody);
    }

    public void flush() throws IOException {
        metaData.writeResponseLine(dos, httpRequest.getProtocolSpec());
        metaData.writeResponseHeaders(dos);
        metaData.writeResponseBody(dos);
        dos.flush();
    }
}
