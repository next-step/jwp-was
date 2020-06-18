package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public class TemplateController extends AbstractController {
	private static final String TEMPLATE_PATH = "./templates";

	@Override
	public HttpResponse doGet(HttpRequest httpRequest) {
		return HttpResponse.from(TEMPLATE_PATH + httpRequest.getPath(), HttpStatus.OK);
	}
}
