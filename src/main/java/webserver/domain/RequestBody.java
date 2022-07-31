package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 요청 몸체 정보
 */
public class RequestBody {
    public static final String DELIMITER = " ";
    public static final String QUERY_DELIMITER = "\\?";
    public static final int PATH_INDEX = 1;
    public static final int QUERYSTRING_INDEX = 1;

    private final Parameters parameters = Parameters.newInstance();

    public RequestBody() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RequestBody(@JsonProperty("requestBody") String line) {
        this.parameters.addParameters(line);
    }

    /**
     * request line에서 queryString을 분리해 요청 정보로 만들고 객체를 반환한다.
     *
     * @param line request line
     * @return 요청 몸체 정보
     */
    public static RequestBody fromRequestLine(String line) {
        String path = line.split(DELIMITER)[PATH_INDEX];
        String[] pathValues = path.split(QUERY_DELIMITER);

        if (existsQueryString(pathValues)) {
            return new RequestBody(pathValues[QUERYSTRING_INDEX]);
        }
        return new RequestBody();
    }

    private static boolean existsQueryString(String[] queryArr) {
        return queryArr.length == 2;
    }

    /**
     * 키/값 목록 문자열을 가공해 추가한다.
     *
     * @param line 키/값 문자열
     */
    public void addAttributes(String line) {
        this.parameters.addParameters(line);
    }

    /**
     * 속성 정보를 찾아 반환한다.
     *
     * @param key 속성 이름
     */
    public String getAttribute(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return parameters.toString();
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
