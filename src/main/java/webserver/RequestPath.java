package webserver;

import java.util.Objects;

public class RequestPath {

	private final String path;

	public RequestPath(final String requestLine) {
		this.path = requestLine.split(" ")[1];
	}

	public String getPath() {
		return path;
	}

	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final RequestPath that = (RequestPath) o;
		return Objects.equals(path, that.path);
	}

	@Override public int hashCode() {
		return Objects.hash(path);
	}
}
