package webserver.http.request.start_line;

import java.util.Objects;

public class HttpVersion {

	private final String version;

	public HttpVersion(final String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final HttpVersion that = (HttpVersion) o;
		return Objects.equals(version, that.version);
	}

	@Override public int hashCode() {
		return Objects.hash(version);
	}
}
