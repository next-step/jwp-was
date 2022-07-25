package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.Resource;

public abstract class AbstractController implements Controller {

	protected final String TEMPLATES_ROOT_PATH = "./templates";

	@Override
	public HttpResponse process(HttpRequest httpRequest) {
		if(HttpMethod.isGet(httpRequest.getMethod())) {
			return doGet(httpRequest);
		}
		return doPost(httpRequest);
	}

	protected String getRootPath(String path) {
		return TEMPLATES_ROOT_PATH + path;
	}

	protected HttpResponse doGet(HttpRequest httpRequest) {
		Resource resource = Resource.of(getRootPath(httpRequest.getPath()));

		return new HttpResponse.Builder()
				.statusLine(httpRequest.getProtocol(), HttpStatus.OK)
				.contentType(resource.getContentType())
				.contentLength(resource.getContentLength())
				.responseBody(resource)
				.build();
	}

	protected HttpResponse doPost(HttpRequest httpRequest) {
		return null;
	}

	protected HttpResponse doRedirect(HttpRequest httpRequest, String location) {
		return new HttpResponse.Builder()
				.statusLine(httpRequest.getProtocol(), HttpStatus.FOUND)
				.location(location)
				.emptyBody()
				.build();
	}

	protected HttpResponse doRedirect(HttpRequest httpRequest, String location, String cookie) {
		return new HttpResponse.Builder()
				.statusLine(httpRequest.getProtocol(), HttpStatus.FOUND)
				.location(location)
				.cookie(cookie)
				.emptyBody()
				.build();
	}
}
