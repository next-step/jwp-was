package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.enums.ContentType;
import http.enums.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private RequestLine requestLine;
    private Header header;
    private String requestBody;
    private Cookie cookie;

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            this.requestLine = RequestLineParser.parse(line.trim());
            this.header = new Header();
            this.cookie = new Cookie();
            this.requestBody = StringUtils.EMPTY;

            while (!StringUtils.EMPTY.equals(line)) {
                line = br.readLine().trim();
                if (StringUtils.isNotEmpty(line)) {
                    header.addHeaderValue(line);
                }
            }

            if (header.isContainsKey("Content-Length")) {
                this.requestBody = IOUtils.readData(br, Integer.parseInt(header.getValue("Content-Length")));
            }

            if (header.isContainsKey("Cookie")) {
                this.cookie.parseCookie(header.getValue("Cookie"));
            }

        } catch (IOException e) {
            logger.error("read http request error : {}", e);
        }
    }

    public Method getMethod() {
        return this.requestLine.getMethod();
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getParameter(String name) {
        return this.requestLine.getParameter(name);
    }

    public boolean isLoggedIn() {
        if (!this.cookie.containsCookieValue("sessionId")) {
            return false;
        }
        HttpSession session = HttpSessionHandler.getSession(this.cookie.getCookieValue("sessionId"));
        return session.getAttribute("user") != null;
    }

    public HttpSession getSession() {
        if(!this.cookie.containsCookieValue("sessionId")) {
            HttpSession session = new HttpSession();
            HttpSessionHandler.addSession(session);
            return session;
        }

        return HttpSessionHandler.getSession(this.cookie.getCookieValue("sessionId"));
    }

    public ContentType getContentType() {
        return ContentType.findContentType(this.requestLine.getPath());
    }
}
