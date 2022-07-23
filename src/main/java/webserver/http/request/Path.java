package webserver.http.request;

import java.util.List;
import java.util.Optional;

public class Path {
    public static final String PATH_DELIMITER = "/";
    private final List<String> parts;
    private final Queries queries;

    public Path(String path, Queries queries) {
        this.parts = List.of(path.split(PATH_DELIMITER));
        this.queries = queries;
    }

    public int getDepth() {
        return parts.size();
    }

    public Optional<String> getPartByDepth(int depth) {
        return (depth < 0 || depth >= getDepth()) ? Optional.empty() : Optional.ofNullable(parts.get(depth));
    }

    public int getDepthOfPart(String part) {
        int depth = parts.indexOf(part);
        if (depth < 0) {
            throw new IllegalArgumentException("잘못된 부분 경로입니다.");
        }
        return depth;
    }

    public Queries getQueries() {
        return queries;
    }
}
