package webserver.request;

import exception.NotExistHttpMethodException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpMethod;
import utils.RequestUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String NOT_EXIST_HTTP_METHOD = "존재하지 않는 HTTP METHOD 입니다.";

    private String uri;
    private HttpMethod method;
    private String protocol;
    private String version;

    private RequestLine() {
    }

    private static class InnerInstanceRequestLine {
        private static final RequestLine instance = new RequestLine();
    }

    public static RequestLine getInstance() {
        return InnerInstanceRequestLine.instance;
    }

    public RequestLine parsing(BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        String[] requestDataArray = firstLine.split(" ");
        String method = requestDataArray[0];
        String path = requestDataArray[1];
        if (!HttpMethod.checkHttpMethod(method)) {
            throw new NotExistHttpMethodException(NOT_EXIST_HTTP_METHOD);
        }

        String[] protocolAndVersion = requestDataArray[2].split("/");
        return setting(HttpMethod.valueOf(method), path, protocolAndVersion[0], protocolAndVersion[1]);
    }

    public String getQueryParam() {

        if (uri.contains("?")) {
            int firstIndex = uri.indexOf("?") + 1;
            return uri.substring(firstIndex);
        }
        return "";
    }

    public User queryStringToUser() {
        String[] queryStringArr = this.uri.split("\\?");
        String queryString = queryStringArr[1];
        logger.debug("queryParam {}", queryString);
        User user = RequestUtils.convertToUser(queryString);
        return user;
    }

    private RequestLine setting(HttpMethod method, String path, String protocol, String version) {
        this.method = method;
        this.uri = path;
        this.protocol = protocol;
        this.version = version;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public String getPathExcludeQueryParam() {
        return uri.split("\\?")[0];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "path='" + uri + '\'' +
                ", method=" + method +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
