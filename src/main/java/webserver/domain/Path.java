package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Path {
    public static final String DELIMITER = "\\?";
    private final String pathStr;
    private final Parameters parameters;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Path(@JsonProperty("path") String path, @JsonProperty("parameters") Parameters parameters) {
        this.pathStr = path;
        this.parameters = parameters;
    }

    public static Path from(String pathWithQuery) {
        String[] values = pathWithQuery.split(DELIMITER);

        if (values.length < 2) {
            return new Path(values[0], Parameters.emptyInstance());
        }

        return new Path(values[0], Parameters.from(values[1]));
    }

    public String getPathStr() {
        return pathStr;
    }

    public Parameters getParameters() {
        return parameters;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path1 = (Path) o;
        return Objects.equals(pathStr, path1.pathStr) && Objects.equals(parameters, path1.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathStr, parameters);
    }
}
