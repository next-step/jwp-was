package model.http;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriPath {
    public static final Pattern PATTERN = Pattern.compile("/?[a-zA-Z0-9\\-._~%!$&'()*+,;=@]+(/[a-zA-Z0-9\\-._~%!$&'()*+,;=:@]+)*/?|/");

    private String path;

    public String getPath() {
        return path;
    }

    private UriPath(String path) {
        this.path = path.toLowerCase();
    }

    public static final UriPath of(String path) {
        if (!validatePattern(path)) throw new IllegalArgumentException("wrong uri path");
        return new UriPath(path);
    }

    private static boolean validatePattern(String path) {
        Matcher matcher = PATTERN.matcher(path);
        return matcher.find() && path.length() == matcher.group().length();
    }

    public Optional<String> getExtension() {
        String endPath = path.substring(path.lastIndexOf('/'));
        int extensionIndex = endPath.lastIndexOf('.');
        String extension = null;
        if (extensionIndex > 0) {
            extension = endPath.substring(extensionIndex);
        }

        return Optional.ofNullable(extension);
    }

    public boolean isSamePath(String path) {
        return this.path.equals(path);
    }

    public UriPath prepend(String pathPrepended) {
        path = trimLastRightSlash(pathPrepended) + path;
        return this;
    }

    private String trimLastRightSlash(String path) {
        if (!StringUtils.isEmpty(path) && path.endsWith("/")) return path.substring(0, path.length() - 1);
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UriPath uriPath1 = (UriPath) o;
        return Objects.equals(path, uriPath1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "UriPath{" +
                "path='" + path + '\'' +
                '}';
    }
}
