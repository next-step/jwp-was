package webserver.request;

import exception.NotExistHttpMethodException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpMethod;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String NOT_EXIST_HTTP_METHOD = "존재하지 않는 HTTP METHOD 입니다.";

    private String uri;
    private HttpMethod method;
    private String protocol;
    private String version;

    public RequestLine(String uri, HttpMethod method, String protocol, String version) {
        this.uri = uri;
        this.method = method;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parsing(BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        String[] requestDataArray = firstLine.split(" ");
        String method = requestDataArray[0];
        String uri = requestDataArray[1];
        if (!HttpMethod.checkHttpMethod(method)) {
            throw new NotExistHttpMethodException(NOT_EXIST_HTTP_METHOD);
        }

        String[] protocolAndVersion = requestDataArray[2].split("/");
        return new RequestLine(uri, HttpMethod.valueOf(method), protocolAndVersion[0], protocolAndVersion[1]);
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
        Map<String, String> userQuery = IOUtils.changeStringToMap(queryString);
        return new User(userQuery.get("userId"), userQuery.get("password"), userQuery.get("name"), userQuery.get("email"));
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
