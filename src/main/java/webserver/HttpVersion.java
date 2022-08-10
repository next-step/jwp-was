package webserver;

import org.springframework.util.Assert;

public class HttpVersion {
	public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP",1,0, false,"HTTP/1.0");
	public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP",1,1, true,"HTTP/1.1");

	private String protocolName;
	private int majorVersion;
	private int minorVersion;
	private boolean keepAlive;
	private String rawVersion;

	public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAlive, String rawVersion) {
		this.protocolName = protocolName;
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.keepAlive = keepAlive;
		this.rawVersion = rawVersion;
	}

	public static HttpVersion valueOf(String version) {
		Assert.notNull(version.trim(), "version must not be null");

		if(HTTP_1_0.isSameAs(version)) {
			return HTTP_1_0;
		}
		if(HTTP_1_1.isSameAs(version)) {
			return HTTP_1_1;
		}

		throw new IllegalArgumentException("Unsupported HTTP version.");
	}

	private boolean isSameAs(String version) {
		return rawVersion.equals(version);
	}
}
