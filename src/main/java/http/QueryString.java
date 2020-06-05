package http;

import utils.FieldNameUtils;

import java.util.*;

public class QueryString {
    private final String fullQueryString;

    public QueryString(String queryString) {
        this.fullQueryString = queryString;
    }

    public String getFullQueryString() {
        return this.fullQueryString;
    }

    public String getParameter(String name) {
        Optional<String> nameValueOptional = Arrays.stream(this.fullQueryString.split("&"))
                .filter(value -> value.startsWith(name))
                .findFirst();

         String[] values = nameValueOptional.orElseThrow(NoSuchElementException::new)
                 .split("=");

         if(values.length < 2) {
             throw new IllegalArgumentException();
         }

         return values[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryString)) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(fullQueryString, that.fullQueryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullQueryString);
    }

    public boolean isContainAllField(Class clazz) {
        try {
            FieldNameUtils.classFieldNames(clazz)
                    .forEach(this::getParameter);
            return true;
        } catch (NoSuchElementException | IllegalAccessError e) {
            return false;
        }
    }
}
