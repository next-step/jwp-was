package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RequestPath {

	private final String path;
	private final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

	public RequestPath(String path) {
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException();
		}
		if (path.contains("?")) {
			parseQueryParameters(path);
			this.path = path.substring(0, path.indexOf("?"));
			return;
		}
		this.path = path;
	}

	private void parseQueryParameters(String path) {
		String queryString = path.substring(path.indexOf("?") + 1);
		String[] queryParameters = queryString.split("&");
		Arrays.stream(queryParameters).forEach(parameter -> {
			String[] nameValuePair = parameter.split("=");
			parameters.put(nameValuePair[0], Collections.singletonList(nameValuePair[1]));
		});
	}

	public String getPath() {
		return path;
	}

	public String getParameter(String name) {
		List<String> values = this.parameters.get(name);
		if (values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}
}
