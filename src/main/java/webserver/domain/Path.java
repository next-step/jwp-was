package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Path {
    public static final String DELIMITER = "\\?";
    private final String path;
    private final Parameters parameters;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Path(@JsonProperty("path") String path, @JsonProperty("parameters") Parameters parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Path from(String pathWithQuery) {
        String[] values = pathWithQuery.split(DELIMITER);

        if (values.length < 2) {
            return new Path(values[0], Parameters.emptyInstance());
        }

        return new Path(values[0], Parameters.from(values[1]));
    }

    public String getPath() {
        return path;
    }

    public Parameters getParameters() {
        return parameters;
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
        return Objects.equals(path, path1.path) && Objects.equals(parameters, path1.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, parameters);
    }
}
