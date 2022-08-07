package webserver.filter;

import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;

public interface GlobalHttpFilter {
    HttpResponseMessage doFilter(HttpRequestMessage request, HttpResponseMessage response);
}
