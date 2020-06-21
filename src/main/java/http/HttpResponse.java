package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private String resourcePath = "";

    private DataOutputStream dos = null;
    private HttpHeaders headers = new HttpHeaders();
    private HttpResponseCode responseCode;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(String url) {
        byte[] body = new byte[0];
        try {
            ContentType contentType = ContentType.getContentType(url);

            resourcePath = contentType.getResourcePath();

            headers.put("Content-Type", contentType.getMimeType());
            body = getBody(url);
            headers.put("Content-Length", body.length + "");

            responseHeader(HttpResponseCode.OK_200);
            responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public byte[] getBody(String url) throws IOException {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(resourcePath + url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public void responseHeader(HttpResponseCode code) {
        try {
            dos.writeBytes(HttpResponseCode.getHeaderLine(code));
            dos.writeBytes(headers.makeResponseHeader());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseTemplateBody(byte[] body) {
        headers.put("Content-Length", body.length + "");
        responseHeader(HttpResponseCode.OK_200);
        responseBody(body);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
