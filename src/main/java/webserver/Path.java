package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Objects;

import static model.Constant.ROOT_PATH;

public class Path {
    private final String path;

    public Path(String path) {
        if (StringUtils.isEmpty(path) || !StringUtils.startsWith(path, ROOT_PATH)) {
            throw new InvalidRequestException("Path");
        }
        this.path = path;
    }

    public String getPath() {
        return path;
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

    @Override
    public String toString() {
        return "Path{" +
                "path='" + path + '\'' +
                '}';
    }
}
