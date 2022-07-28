package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * 경로 정보
 */
public class Path {
    private final String pathStr;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Path(@JsonProperty("path") String path) {
        this.pathStr = path;
    }

    public String getPathStr() {
        return pathStr;
    }

    public boolean containsPath(Path path) {
        return this.pathStr.startsWith(path.pathStr);
    }

    @Override
    public String toString() {
        return pathStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Path)) {
            return false;
        }
        Path path = (Path) o;
        return Objects.equals(pathStr, path.pathStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathStr);
    }
}
