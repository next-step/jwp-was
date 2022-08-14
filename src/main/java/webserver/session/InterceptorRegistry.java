package webserver.session;

import java.util.ArrayList;
import java.util.List;

public class InterceptorRegistry {

    private static List<Interceptor> interceptors = new ArrayList<>();

    static {
        interceptors.add(new SessionInterceptor());
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }
}
