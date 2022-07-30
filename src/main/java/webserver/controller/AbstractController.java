package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractController implements Controller {

	@Override
	public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
		if(HttpMethod.isGet(httpRequest.getMethod())) {
			doGet(httpRequest, httpResponse);
		}
		doPost(httpRequest, httpResponse);
	}

	protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		httpResponse.forward(httpRequest.getPath());
	}

	protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
	}
}
