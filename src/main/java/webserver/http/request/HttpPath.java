package webserver.http.request;

import java.util.Objects;

public class HttpPath {
	private static final String PATH_PARAM_DELIMITER = "\\?";
	private static final String MESSAGE_INVALID_PATH_INFO = "유효하지 않은 PATH 정보 입니다.";
	private static final int PATH_PARAM_LENGTH = 2;
	private static final int INDEX_PATH = 0;
	private static final int INDEX_PARAMETER = 1;

	private String path;
	private QueryParameter parameters;

	private HttpPath(String path) {
		this.path = path;
		this.parameters = QueryParameter.of();
	}

	private HttpPath(String path, String parameters) {
		this.path = path;
		this.parameters = QueryParameter.of(parameters);
	}

	public static HttpPath of(String attribute) {
		if (attribute.isBlank()) {
			throw new IllegalArgumentException(MESSAGE_INVALID_PATH_INFO);
		}

		String[] pathWithParameters = attribute.split(PATH_PARAM_DELIMITER);
		if (pathWithParameters.length == PATH_PARAM_LENGTH) {
			return new HttpPath(pathWithParameters[INDEX_PATH], pathWithParameters[INDEX_PARAMETER]);
		}
		return new HttpPath(pathWithParameters[INDEX_PATH]);
	}

	public String getPath() {
		return path;
	}

	public QueryParameter getParameter() {
		return parameters;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HttpPath httpPath = (HttpPath) o;
		return Objects.equals(path, httpPath.path) && Objects.equals(parameters, httpPath.parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, parameters);
	}
}
