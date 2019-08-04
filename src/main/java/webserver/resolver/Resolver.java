package webserver.resolver;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-05.
 */
public interface Resolver {
    HttpResponse resolve(HttpRequest httpRequest);
}
