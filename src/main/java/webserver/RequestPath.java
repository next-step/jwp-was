package webserver;

import java.util.Objects;
import org.apache.logging.log4j.util.Strings;

public class RequestPath {

	private final String path;
	private final String query;

	public RequestPath(final String requestLine) {
		final String path = requestLine.split(" ")[1];

		if (path.contains("?")) {
			final String[] split = path.split("\\?");
			this.path = split[0];
			this.query = split[1];
			return;
		}
		this.path = path;
		this.query = Strings.EMPTY;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
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
