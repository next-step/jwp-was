package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {
	void process(HttpRequest httpRequest, HttpResponse httpResponse);
}
