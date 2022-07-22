package webserver;

import java.util.Objects;
import java.util.regex.Pattern;

public class Path {
    private static final int VALID_NUMBER_OF_PROPERTIES = 2;
    private static final String PATH_AND_DATA_DELIMITER = "?";
    private static final String START_CHARACTER_OF_PATH = "/";

    private final String path;
    private final String data;

    private Path(String path, String data) {
        this.path = path;
        this.data = data;
    }

    private Path(String path) {
        this(path, null);
    }

    public static Path from(String path) {
        validate(path);
        if (!hasData(path)) {
            return new Path(path);
        }
        String[] properties = path.split(Pattern.quote(PATH_AND_DATA_DELIMITER));
        validateProperties(properties);
        return new Path(properties[0], properties[1]);
    }

    private static boolean hasData(String path) {
        return path.contains(PATH_AND_DATA_DELIMITER);
    }

    private static void validate(String path) {
        if (!path.startsWith(START_CHARACTER_OF_PATH)) {
            throw new IllegalArgumentException("잘못된 형식의 path입니다.");
        }
    }

    private static void validateProperties(String[] properties) {
        if (properties.length != VALID_NUMBER_OF_PROPERTIES) {
            throw new IllegalArgumentException(String.format("필요한 속성의 개수[%d]를 만족하지 않습니다.", VALID_NUMBER_OF_PROPERTIES));
        }
    }

    public String getPath() {
        return path;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) && Objects.equals(data, path1.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, data);
    }

    @Override
    public String toString() {
        return "Path{" +
                "path='" + path + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
