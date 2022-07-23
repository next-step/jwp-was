package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class StaticController extends AbstractController {

	private static final String RESOURCE_PATH = "./static";

	@Override
	protected String getResourcePath() {
		return RESOURCE_PATH;
	}

	@Override
	HttpResponse doPost(HttpRequest httpRequest) {
		return null;
	}
}
