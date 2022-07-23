package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class IndexController extends AbstractController {

	private static final String RESOURCE_PATH = "./templates";

	@Override
	protected String getResourcePath() {
		return RESOURCE_PATH;
	}

	@Override
	public HttpResponse doPost(HttpRequest httpRequest) {
		return null;
	}
}
