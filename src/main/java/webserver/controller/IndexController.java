package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.response.Resource;

public class IndexController implements Controller {
	@Override
	public HttpResponse process(HttpRequest httpRequest) {
		if(HttpMethod.isGet(httpRequest.getMethod())) {
			return doGet(httpRequest);
		}
		return doPost(httpRequest);
	}

	@Override
	public HttpResponse doGet(HttpRequest httpRequest) {
		Resource resource = Resource.of("./templates" + httpRequest.getPath());

		return new HttpResponse.Builder()
				.statusLine(httpRequest.getProtocol(), HttpStatus.OK)
				.contentType(resource.getContentType())
				.contentLength(resource.getContentLength())
				.responseBody(resource)
				.build();
	}

	@Override
	public HttpResponse doPost(HttpRequest httpRequest) {
		return null;
	}
}
