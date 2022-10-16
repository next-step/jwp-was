package webserver.http;

public enum HttpMethod {
	GET, POST;

	public static HttpMethod of(String method) {
		return HttpMethod.valueOf(method.toUpperCase());
	}
}
