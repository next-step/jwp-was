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
    private Cookies cookies = new Cookies();
    private HttpResponseCode httpResponseCode;
    private byte[] body;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(String url) {
        try {
            ContentType contentType = ContentType.getContentType(url);

            resourcePath = contentType.getResourcePath();

            headers.put("Content-Type", contentType.getMimeType());
            this.body = getBody(url);
            headers.put("Content-Length", body.length + "");

            this.httpResponseCode = HttpResponseCode.OK_200;
            responseWrite();
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

    public void sendRedirect(HttpResponseCode responseCode) {
        this.httpResponseCode = responseCode;
        responseWrite();
    }

    public void responseWrite() {
        try {
            dos.writeBytes(HttpResponseCode.getHeaderLine(httpResponseCode));
            dos.writeBytes(headers.makeResponseHeader());
            dos.writeBytes(cookies.makeCookie());
            dos.writeBytes("\r\n");
            if(body != null) {
                dos.write(body, 0, body.length);
                dos.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseTemplateBody(byte[] body) {
        headers.put("Content-Length", body.length + "");
        this.httpResponseCode = HttpResponseCode.OK_200;
        this.body = body;
        responseWrite();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(String key, String value) { cookies.put(key, value);}

}
