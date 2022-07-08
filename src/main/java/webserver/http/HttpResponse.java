package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;

    private Map<String, String> headers = new HashMap<>();

    private HttpStatus httpStatus;

    private String body;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void ok() {
        ok(null);
    }

    public void ok(byte[] body) {
        httpStatus = HttpStatus.OK;

        response(dos, body);
    }

    public void redirect(String location) {
        redirect(HttpStatus.FOUND, location);
    }

    public void redirect(HttpStatus httpStatus, String location) {
        this.httpStatus = httpStatus;

        addHeader("Content-Type", MediaType.TEXT_HTML_UTF8.toString());
        addHeader("Location", location);

        response(dos, null);
    }


    public void notfound() {
        httpStatus = HttpStatus.NOT_FOUND;

        addHeader("Content-Type", MediaType.TEXT_HTML_UTF8.toString());

        response(dos, null);
    }

    private void response(DataOutputStream dos, byte[] body) {
        byte[] bytes = null;
        try {
            bytes = toBytes(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        write(dos, bytes);
    }

    private byte[] toBytes(byte[] body) throws IOException, URISyntaxException {
        if (body == null) {
            return getHeader(0);
        }

        byte[] header = getHeader(body.length);
        return merge(header, body);
    }

    private byte[] getHeader(long bodyLength) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s %s \r\n", Protocol.HTTP_1_1, httpStatus.toString()));

        for (String key : headers.keySet()) {
            stringBuilder.append(String.format("%s: %s \r\n", key, headers.get(key)));
        }

        stringBuilder.append(String.format("Content-Length: %s \r\n", bodyLength));

        stringBuilder.append("\r\n");

        return stringBuilder.toString().getBytes();
    }

    private byte[] merge(byte[] header, byte[] body) {
        byte[] responseBytes = new byte[header.length + body.length];
        System.arraycopy(header, 0, responseBytes, 0, header.length);
        System.arraycopy(body, 0, responseBytes, header.length, body.length);
        return responseBytes;
    }

    private void write(DataOutputStream dos, byte[] response) {
        try {
            dos.write(response, 0, response.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
