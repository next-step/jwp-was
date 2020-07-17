package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private String resourcePath = "";

    private DataOutputStream dos = null;
    private HttpHeaders headers = new HttpHeaders();
    private List<Cookie> cookies = new ArrayList<>();
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
            dos.writeBytes(makeCookie());
            dos.writeBytes("\r\n");
            if(body != null) {
                dos.write(body, 0, body.length);
                dos.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String makeCookie() {
        String cookieString = "";

        for(Cookie cookie : this.cookies) {
            cookieString += String.format("%s: %s", "Set-Cookie", getHeaderCookieString(cookie));
            cookieString += "\r\n";
        }

        return cookieString;
    }

    private String getHeaderCookieString(Cookie cookie) {
        List<String> cookieString = new ArrayList<>();

        Set<String> keys = cookie.keySet();
        for(String key: keys) {
            StringBuilder builder = new StringBuilder();;
            builder.append(key)
                    .append("=")
                    .append(cookie.get(key));
            cookieString.add(builder.toString());
        }

        return cookieString.stream()
                .filter(str -> !StringUtils.isEmpty(str))
                .collect(Collectors.joining("; "));
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

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

}
