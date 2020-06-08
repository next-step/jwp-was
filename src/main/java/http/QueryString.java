package http;

import http.Const.HttpConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FieldNameUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class QueryString {
    private static final Logger log = LoggerFactory.getLogger(QueryString.class);

    private final String fullQueryString;

    public QueryString(String queryString) {
        String decodeQuery = queryString;
        try {
            decodeQuery = URLDecoder.decode(queryString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("url decode error : {}" , queryString);
        }
        this.fullQueryString = decodeQuery;
    }

    public String getFullQueryString() {
        return this.fullQueryString;
    }

    public String getParameter(String name) {
        Optional<String> nameValueOptional = Arrays.stream(this.fullQueryString.split(HttpConst.QUERY_SEPARATOR))
                .filter(value -> value.startsWith(name))
                .findFirst();

         String[] values = nameValueOptional.orElseThrow(NoSuchElementException::new)
                 .split(HttpConst.QUERY_VALUE_SEPARATOR);

         if(values.length < 2) {
             throw new IllegalArgumentException("Invalid Body String : " + name);
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
