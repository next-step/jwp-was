package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Servlet {
	void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
