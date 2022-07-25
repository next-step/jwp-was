package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.Resource;

public abstract class AbstractController implements Controller {

	private static final String RESOURCE_PATH = "./templates";

	@Override
	public HttpResponse process(HttpRequest httpRequest) {
		if(HttpMethod.isGet(httpRequest.getMethod())) {
			return doGet(httpRequest);
		}
		return doPost(httpRequest);
	}

	protected String getResourcePath() {
		return RESOURCE_PATH;
	}

	protected HttpResponse doGet(HttpRequest httpRequest) {
		Resource resource = Resource.of(getResourcePath() + httpRequest.getPath());

		return new HttpResponse.Builder()
				.statusLine(httpRequest.getProtocol(), HttpStatus.OK)
				.contentType(resource.getContentType())
				.contentLength(resource.getContentLength())
				.responseBody(resource)
				.build();
	}

	abstract HttpResponse doPost(HttpRequest httpRequest);

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
