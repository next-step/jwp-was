package webserver.request.enums;

/**
 * Created by hspark on 2019-08-01.
 */
public enum HttpMethod {
    GET, POST, DELETE, PUT;

    public boolean isPost() {
        return this == HttpMethod.POST;
    }

    public boolean isGet() {
        return this == HttpMethod.GET;
    }
}
