package webserver.filter;

import webserver.http.header.HttpCookie;
import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;
import webserver.http.session.HttpSessionId;

import static webserver.http.session.HttpSessionStore.SESSION_ID_KEY;

@GlobalFilterOrder(1)
public class HttpSessionFilter implements GlobalHttpFilter {
    @Override
    public HttpResponseMessage doFilter(HttpRequestMessage request, HttpResponseMessage response) {
        if (request.isNotExistSession()) {
            HttpSessionId httpSessionId = HttpSessionId.create();
            HttpCookie httpCookie = new HttpCookie(SESSION_ID_KEY, httpSessionId.getId());

            response.addCookie(httpCookie);
        }

        return response;
    }
}
