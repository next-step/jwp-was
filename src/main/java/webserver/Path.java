package webserver;

import java.util.Objects;

public class Path {
    private static final int VALID_NUMBER_OF_TOKENS = 2;
    private static final String PATH_AND_QUERY_DELIMITER = "\\?";
    private static final String START_CHARACTER_OF_PATH = "/";

    private final String path;
    private final String queryString;

	public Path(String line) {
		this(parsed(line));
	}
	public Path(String[] line) {
		this(line[0], line[1]);
	}

	public Path(String path, String queryString) {
		validatePath(path);

		this.path = path;
		this.queryString = queryString;
	}

	private static String[] parsed(String line) {
		String[] tokens = line.split(PATH_AND_QUERY_DELIMITER);
		validateTokens(tokens);

		if (tokens.length == 1) {
			return new String[]{tokens[0], null};
		}
		return new String[]{tokens[0], tokens[1]};
	}

    private static void validatePath(String path) {
        if (!path.startsWith(START_CHARACTER_OF_PATH)) {
            throw new IllegalArgumentException("잘못된 형식의 path입니다.");
        }
    }

    private static void validateTokens(String[] tokens) {
        if (tokens.length > VALID_NUMBER_OF_TOKENS) {
            throw new IllegalArgumentException(String.format("필요한 속성의 개수[%d]를 만족하지 않습니다.", VALID_NUMBER_OF_TOKENS));
        }
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) && Objects.equals(queryString, path1.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }

    @Override
    public String toString() {
        return "Path{" +
                "path='" + path + '\'' +
                ", queryString='" + queryString + '\'' +
                '}';
    }
}
