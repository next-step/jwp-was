package http.request;

public class Protocol {

	private static final String DELIMITER = "/";

	private final ProtocolType protocolType;
	private final String version;

	private Protocol(ProtocolType protocolType, String version) {
		this.protocolType = protocolType;
		this.version = version;
	}

	public static Protocol of(String datum) {
		var protocolWithVersion = datum.split(DELIMITER);
		var protocolType = ProtocolType.valueOf(protocolWithVersion[0]);

		return new Protocol(protocolType, protocolWithVersion[1]);
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public String getVersion() {
		return version;
	}
}
