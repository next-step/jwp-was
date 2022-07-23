package webserver.http.response;

public class HttpResponseBody {
	private final Resource resource;

	public HttpResponseBody(Resource resource) {
		this.resource = resource;
	}

	public String getBody() {
		return new String(resource.getResource());
	}
}
