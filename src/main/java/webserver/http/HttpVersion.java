package webserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpVersion implements Comparable<HttpVersion> {
    private static final String HTTP = "HTTP";
    private static final Pattern HTTP_VERSION_PATTERN = Pattern.compile(
            "^" + HTTP + "\\/(\\d+).(\\d+)$"
    );

    private final int major;
    private final int minor;

    private HttpVersion(final int major, final int minor) {
        this.major = major;
        this.minor = minor;
    }

    static HttpVersion parse(final String field) {
        final Matcher matcher = HTTP_VERSION_PATTERN.matcher(field);
        if (matcher.matches()) {
            return new HttpVersion(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        }
        throw new ParseException();
    }

    boolean lowerThan(final HttpVersion that) {
        return compareTo(that) < 0;
    }

    int getMajor() {
        return major;
    }

    int getMinor() {
        return minor;
    }

    @Override
    public int compareTo(final HttpVersion o) {
        int delta = this.major - o.major;
        if (delta == 0) {
            delta = this.minor - o.minor;
        }
        return delta;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(HTTP)
                .append('/')
                .append(major)
                .append('.')
                .append(minor)
                .toString()
                ;
    }
}
