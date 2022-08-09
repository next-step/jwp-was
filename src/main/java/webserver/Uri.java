package webserver;

import java.util.Map;

public class Uri {
	private String uri;
	private String path;
	private Map<String, String> params;

	public Uri(String path, Map<String, String> params) {
		this.path = path;
		this.params = params;
	}

	public String getUri() {
		return uri;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getParams() {
		return params;
	}
}
