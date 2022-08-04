package response;

import constant.HttpContentType;
import constant.HttpHeader;
import constant.HttpStatusCode;
import request.HttpRequest;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpResponse {

    private HttpStatusCode code;
    private ResponseHeader header;
    private byte[] body;
    private DataOutputStream dos;

    public HttpResponse(HttpStatusCode code, ResponseHeader header, byte[] body, DataOutputStream dos) {
        this.code = code;
        this.header = header;
        this.body = body;
        this.dos = dos;
    }

    public static HttpResponse parse(OutputStream out) {
        return new HttpResponse(HttpStatusCode.OK, ResponseHeader.empty(), new byte[0], new DataOutputStream(out));
    }

    public void forward(String view) throws IOException, URISyntaxException {
        HttpContentType contentType = HttpContentType.of(view);

        addBody(FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + view), contentType);
        response200Header(body.length);
    }

    public void sendRedirect(String view) throws IOException, URISyntaxException {
        HttpContentType contentType = HttpContentType.of(view);

        this.code = HttpStatusCode.FOUND;
        this.header.add(HttpHeader.LOCATION.getValue(), view);

        addBody(FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + view), contentType);
        response200Header(body.length);
    }

    public void forwardBody(String templatePage) throws IOException, URISyntaxException {
        addBody(templatePage.getBytes(StandardCharsets.UTF_8), HttpContentType.TEXT_HTML);
        response200Header(body.length);
    }

    private void addBody(byte[] body, HttpContentType contentType) {
        this.body = body;

        this.header.add(HttpHeader.CONTENT_LENGTH.getValue(), String.valueOf(this.body.length));
        this.header.add(HttpHeader.CONTENT_TYPE.getValue(), contentType.getValue());
    }

    public void addHeader(String key, String value) {
        header.add(key, value);
    }

    public void response200Header(int bodyLength) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %s \r\n", getResponseCode()));
            processHeaders();
            dos.writeBytes("\r\n");
            dos.write(getBody(), 0, getBody().length);
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processHeaders() {
        try {
            for (Map.Entry<String, String> entry : header.entries()) {
                dos.writeBytes(entry.getKey() + ": " + header.getHeader(entry.getKey()) + " \r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResponseCode() {
        return code.getCode() + " " + code.getMessage();
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }
}
