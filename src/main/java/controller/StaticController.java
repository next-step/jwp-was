package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public class StaticController extends AbstractController {
	private static final String STATIC_PATH = "./static";

	@Override
	public HttpResponse doGet(HttpRequest httpRequest) {
		return HttpResponse.from(STATIC_PATH + httpRequest.getPath(), HttpStatus.OK);
	}
}
