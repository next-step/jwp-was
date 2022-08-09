package webserver;

import org.springframework.util.Assert;

public class HttpVersion {
	public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP",1,0, false,"HTTP/1.0");
	public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP",1,1, true,"HTTP/1.1");

	private String protocolName;
	private int majorVersion;
	private int minorVersion;
	private boolean keepAlive;

	private String versionString;

	public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAlive, String versionString) {
		this.protocolName = protocolName;
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.keepAlive = keepAlive;
		this.versionString = versionString;
	}

	public static HttpVersion valueOf(String version) {
		Assert.notNull(version.trim(), "version must not be null");

		if(version.equals(HTTP_1_0.versionString)) {
			return HTTP_1_0;
		}
		if(version.equals(HTTP_1_1.versionString)) {
			return HTTP_1_1;
		}
		throw new IllegalArgumentException("지원하지 않는 버전입니다.");
	}

	public String getVersionString() {
		return versionString;
	}
}
