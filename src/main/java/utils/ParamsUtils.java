package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParamsUtils {
    private ParamsUtils(){}

    public static Map<String, String> parsedQueryString(String query) {
        Map<String, String> params;
        String[] tokens = query.split("&");
        params = Arrays.stream(tokens)
                .map(token -> Property.parsed(token, "="))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Property::getKey, Property::getValue, (a, b) -> b));
        return params;
    }

    static class Property {
        private String key;
        private String value;

        private Property(String key, String value) {
            this.key = key;
            this.value = value;
        }

        static Property parsed(String keyValue, String regex) {
            if (keyValue == null && keyValue.isBlank()) {
                return null;
            }

            String[] token = keyValue.split(regex);
            if (token.length != 2 ) {
                return null;
            }

            return new Property(token[0], token[1]);
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Property property = (Property) o;
            return Objects.equals(key, property.key) && Objects.equals(value, property.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return "KeyValue{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
