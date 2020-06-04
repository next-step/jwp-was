package http;

import java.util.*;

public class QueryString {
    private final String fullQueryString;

    public QueryString(String queryString) {
        this.fullQueryString = queryString;
    }

    public String getParameter(String name) {
        Optional<String> nameValueOptional = Arrays.stream(this.fullQueryString.split("&"))
                .filter(value -> value.startsWith(name))
                .findFirst();

         String[] values = nameValueOptional.orElseThrow(() -> new NoSuchElementException())
                 .split("=");

         if(values.length < 2) {
             throw new IllegalArgumentException();
         }

         return values[1];
    }

}
