package model.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class HttpRequestBody {
    private Query query;

    private HttpRequestBody(Query query) {
        this.query = query;
    }

    public static HttpRequestBody of(BufferedReader reader, ContentLength contentLength) throws IOException {
        return new HttpRequestBody(Query.of(IOUtils.readData(reader, contentLength.getLength())));
    }

    public Query getQuery() {
        return query;
    }

    public Optional<QueryParameter> findDataByName(String name) {
        return query.findByName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestBody that = (HttpRequestBody) o;
        return Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query);
    }

    @Override
    public String toString() {
        return "HttpRequestBody{" +
                "query=" + query +
                '}';
    }
}
