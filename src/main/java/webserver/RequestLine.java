package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

import static model.Constant.*;

public class RequestLine {
    private HttpMethod httpMethod;
    private Path path;
    private QueryString queryString;
    private Protocol protocol;
    private Version version;

    public RequestLine(String requestValue) {
        String[] values = requestValue.split(VALUE_SPERATOR);
        String[] pathAndQueryString = StringUtils.split(values[1], PATH_AND_QUERY_STRING_SPERATOR);
        String[] protocolAndVersion = values[2].split(PROTOCOL_AND_VALUE_SPERATOR);

        this.httpMethod = HttpMethod.valueOf(values[0]);
        this.path = new Path(values[1]);
        if (pathAndQueryString.length > 1) {
            this.path = new Path(pathAndQueryString[0]);
            this.queryString = new QueryString(pathAndQueryString[1]);
        }
        this.protocol = new Protocol(protocolAndVersion[0]);
        this.version = new Version(protocolAndVersion[1]);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path.getPath();
    }

    public Map<String, String> getQueryString() {
        return queryString.getParameter();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return version.getVersion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && Objects.equals(path, that.path) && Objects.equals(queryString, that.queryString) && Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, queryString, protocol, version);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod +
                ", path=" + path +
                ", queryString=" + queryString +
                ", protocol=" + protocol +
                ", version=" + version +
                '}';
    }
}
