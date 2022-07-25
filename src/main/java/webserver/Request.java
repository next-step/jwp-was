package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Header;
import webserver.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static model.Constant.ROOT_PATH;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private RequestLine requestLine;
    private Header header;

    public Request(List<String> requests) {
        this.requestLine = new RequestLine(requests.get(0));
        logger.debug("Request : {}", requests.get(0));
        requests.remove(0);
        this.header = new Header(requests);

    }

    public Request(RequestLine requestLine, Header header) {
        this.requestLine = requestLine;
        this.header = header;
    }

    public String getRequestPath() {
        return StringUtils.equals(requestLine.getPathWithoutQueryString(), ROOT_PATH) ? getRedirectUrl() : requestLine.getPathWithoutQueryString();
    }

    public Map<String, String> getRequestQueryString() {
        return requestLine.getQueryStringWithoutPathFromPath();
    }

    private String getRedirectUrl() {
        return "/index.html";
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(requestLine, request.requestLine) && Objects.equals(header, request.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, header);
    }
}
