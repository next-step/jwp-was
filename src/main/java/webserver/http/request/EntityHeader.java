package webserver.http.request;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityHeader {

    private static final String TOKEN = ":";

    private final Map<EntityHeaderFields, String> entityHeaders = new HashMap<>();

    public EntityHeader(final List<String> httpRequestHeaders) {
        if (CollectionUtils.isEmpty(httpRequestHeaders)) {
            return;
        }

        setEntityHeaders(httpRequestHeaders);
    }

    public String get(EntityHeaderFields field) {
        return entityHeaders.getOrDefault(field, "");
    }

    private void setEntityHeaders(final List<String> httpRequestHeaders) {
        httpRequestHeaders.stream()
                .filter(EntityHeaderFields::contains)
                .forEach(field -> {
                    final String[] splitFields = splitField(field);
                    if (splitFields == null) {
                        return;
                    }

                    final EntityHeaderFields key = parseKey(splitFields);
                    final String value = parseValue(splitFields);

                    this.entityHeaders.put(key, value);
                });
    }

    private String[] splitField(final String field) {
        if (!StringUtils.contains(field, TOKEN)) {
            return null;
        }

        return field.split(TOKEN);
    }

    private EntityHeaderFields parseKey(final String[] splitFields) {
        final int INDEX_OF_KEY = 0;
        final String key = StringUtils.trim(splitFields[INDEX_OF_KEY]);

        return EntityHeaderFields.valueOfName(key);
    }

    private String parseValue(final String[] splitFields) {
        final int INDEX_OF_VALUE = 1;

        return StringUtils.trim(splitFields[INDEX_OF_VALUE]);
    }
}
