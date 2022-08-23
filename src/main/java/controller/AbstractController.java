package controller;

import webserver.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class AbstractController implements Controller {
	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
		HttpMethod method = request.getMethod();

		if (method.equals(HttpMethod.GET)) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}

	void doPost(HttpRequest request, HttpResponse response) throws Exception {

	}

	void doGet(HttpRequest request, HttpResponse response) throws Exception {

	}
}
