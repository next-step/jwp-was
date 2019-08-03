package webserver;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path {
    public static final Pattern PATTERN = Pattern.compile("/?[a-zA-Z0-9\\-._~%!$&'()*+,;=@]+(/[a-zA-Z0-9\\-._~%!$&'()*+,;=:@]+)*/?|/");

    private String path;

    private Path(String path) {
        this.path = path.toLowerCase();
    }

    public static final Path of(String path) {
        if (!validatePattern(path)) throw new IllegalArgumentException("wrong uri path");
        return new Path(path);
    }

    private static boolean validatePattern(String path) {
        Matcher matcher = PATTERN.matcher(path);
        return matcher.find() && path.length() == matcher.group().length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
