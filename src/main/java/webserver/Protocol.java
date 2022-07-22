package webserver;

import java.util.Objects;

public class Protocol {

	private final String protocol;

	public Protocol(final String protocol) {
		this.protocol = protocol;
	}

	public String getProtocol() {
		return protocol;
	}

	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Protocol protocol1 = (Protocol) o;
		return Objects.equals(protocol, protocol1.protocol);
	}

	@Override public int hashCode() {
		return Objects.hash(protocol);
	}
}
