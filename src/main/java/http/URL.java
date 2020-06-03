package http;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class URL {

    private final String path;
    private final String queryString;

    public URL(String url) {
        String[] values = url.split("\\?");
        String queryString = "";
        this.path = values[0];
        if (values.length > 1) {
            queryString = values[1];
        }
        this.queryString = queryString;
    }

    public String getPath() {
        return this.path;
    }

    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof URL)) {
            return false;
        }

        URL url = (URL) o;

        if (path != null ? !path.equals(url.path) : url.path != null) {
            return false;
        }
        return queryString != null ? queryString.equals(url.queryString) : url.queryString == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
        return result;
    }
}
