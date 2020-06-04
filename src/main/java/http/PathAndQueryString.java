package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import model.User;

public class PathAndQueryString {

    private QueryString queryString;
    private String path;

    public PathAndQueryString(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public String getPath() {
        return this.path;
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

    public boolean isSignUrl() {
        return "/create".equals(this.path) && StringUtils.isNotEmpty(this.queryString.getFullQueryString()) && this.queryString.isContainAllField(User.class);
    }
}
