package webserver.http.response;

public class HttpResponseThreadLocal {

    public static final ThreadLocal<HttpResponse> threadLocal = ThreadLocal.withInitial(() -> null);
}
