package webserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpVersion {
    private static final Pattern HTTP_VERSION_PATTERN = Pattern.compile("^HTTP\\/(\\d+).(\\d+)$");

    private final int major;
    private final int minor;

    private HttpVersion(final int major, final int minor) {
        this.major = major;
        this.minor = minor;
    }

    static HttpVersion parse(final String field) {
        final Matcher matcher = HTTP_VERSION_PATTERN.matcher(field);
        matcher.matches();
        return new HttpVersion(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    int getMajor() {
        return major;
    }

    int getMinor() {
        return minor;
    }

    @Override
    public String toString() {
        return "HttpVersion{" +
                "major=" + major +
                ", minor=" + minor +
                '}';
    }
}
