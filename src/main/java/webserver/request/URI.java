package webserver.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import utils.FileUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by hspark on 2019-08-01.
 */
public class URI {
	public static final String QUERY_STRING_DELIMETER = "\\?";

	public static final int QUERY_STRING_INDEX = 2;

	private String path;
	private String queryString;
	private RequestParameters requestParameters;

	public URI(String path, String queryString) {
		this.path = path;
		this.queryString = queryString;
		this.requestParameters = RequestParameters.parse(queryString);
	}

	public static URI parse(String uri) {
		String[] parsedUri = uri.split(QUERY_STRING_DELIMETER);
		String path = parsedUri[0];
		String queryString = parsedUri.length < QUERY_STRING_INDEX ? StringUtils.EMPTY : parsedUri[1];
		return new URI(path, queryString);
	}

	public String getPath() {
		return path;
	}

	public String getQueryString() {
		return queryString;
	}

	public Optional<String> get(String key) {
		return requestParameters.getOne(key);
	}

	public List<String> getAll(String key) {
		return requestParameters.getAll(key);
	}

	public boolean isFile() {
        return FileUtils.hasComma(path);
    }


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		URI uri = (URI) o;

		if (!Objects.equals(path, uri.path))
			return false;
		if (!Objects.equals(queryString, uri.queryString))
			return false;
		return Objects.equals(requestParameters, uri.requestParameters);

	}

	@Override
	public int hashCode() {
		int result = path != null ? path.hashCode() : 0;
		result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
		result = 31 * result + (requestParameters != null ? requestParameters.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "URI{" +
				"path='" + path + '\'' +
				", queryString='" + queryString + '\'' +
				", requestParameters=" + requestParameters +
				'}';
	}
}
