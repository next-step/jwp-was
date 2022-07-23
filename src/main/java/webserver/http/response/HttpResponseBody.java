package webserver.http.response;

public class HttpResponseBody {
	private final String body;

	public HttpResponseBody(String body) {
		this.body = body;
	}

	public HttpResponseBody(Resource resource) {
		this(new String(resource.getResource()));
	}

	public String getBody() {
		return body;
	}
}
