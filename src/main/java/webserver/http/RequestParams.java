package webserver.http;

import utils.ParamsUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {
	private String queryString;
	private String body;

	public RequestParams(String queryString, String body) {
		this.queryString = queryString;
		this.body = body;
	}

	public Map<String, String> all() {
		Map<String, String> params = new HashMap<>();
		putParams(params, queryString);
		putParams(params, body);
		return params;
	}

	public String value(String name) {
		return all().get(name)
;	}

	private void putParams(Map<String, String> params, String data) {
		if (data != null && !data.isBlank()) {
			params.putAll(ParamsUtils.parsedQueryString(data));
		}
	}
}
