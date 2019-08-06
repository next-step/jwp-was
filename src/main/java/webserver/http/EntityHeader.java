package webserver.http;

import exception.AlreadyExistsException;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import webserver.http.request.RequestHeaderFields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class EntityHeader {

    private static final String TOKEN = ":";
    private static final String CRLF = "\r\n";

    private final Map<EntityHeaderFields, String> entityHeaders = new HashMap<>();
    private final Map<String, String> extensionHeaders = new HashMap<>();

    public EntityHeader(final List<String> httpRequestHeaders) {
        if (CollectionUtils.isEmpty(httpRequestHeaders)) {
            return;
        }

        setEntityHeaders(httpRequestHeaders);
        setExtensionHeaders(httpRequestHeaders);
    }

    public String get(EntityHeaderFields field) {
        return entityHeaders.getOrDefault(field, "");
    }

    public String get(String key) {
        return extensionHeaders.getOrDefault(key, "");
    }

    public void put(EntityHeaderFields key, String value) {
        if (entityHeaders.containsKey(key)) {
            throw new AlreadyExistsException(String.format("이미 존재하는 필드 입니다. 입력값 : %s", key.getName()));
        }

        entityHeaders.put(key, value);
    }

    public void put(String key, String value) {
        if (extensionHeaders.containsKey(key)) {
            throw new AlreadyExistsException(String.format("이미 존재하는 필드 입니다. 입력값 : %s", key));
        }

        extensionHeaders.put(key, value);
    }

    @Override
    public String toString() {
        final String entityHeader = entityHeaders.entrySet().stream()
                .map(header -> header.getKey().getName() + ": " + header.getValue())
                .collect(Collectors.joining(CRLF));

        final String extensionHeader = extensionHeaders.entrySet().stream()
                .map(header -> header.getKey() + ": " + header.getValue())
                .collect(Collectors.joining(CRLF));

        return entityHeader + CRLF + extensionHeader;
    }

    private void setEntityHeaders(final List<String> httpRequestHeaders) {
        httpRequestHeaders.stream()
                .filter(EntityHeaderFields::contains)
                .forEach(field -> {
                    final String[] splitFields = splitField(field);
                    if (splitFields == null) {
                        return;
                    }

                    final EntityHeaderFields key = parseField(splitFields);
                    final String value = parseValue(splitFields);

                    this.entityHeaders.put(key, value);
                });
    }

    private void setExtensionHeaders(final List<String> httpRequestHeaders) {
        httpRequestHeaders.stream()
                .filter(header -> !GeneralHeaderFields.anyMatch(header))
                .filter(header -> !RequestHeaderFields.anyMatch(header))
                .filter(header -> !EntityHeaderFields.anyMatch(header))
                .forEach(field -> {
                    final String[] splitFields = splitField(field);
                    if (splitFields == null) {
                        return;
                    }

                    final String key = parseKey(splitFields);
                    final String value = parseValue(splitFields);

                    this.extensionHeaders.put(key, value);
                });
    }

    private String[] splitField(final String field) {
        if (!StringUtils.contains(field, TOKEN)) {
            return null;
        }

        return field.split(TOKEN);
    }

    private EntityHeaderFields parseField(final String[] splitFields) {
        final int INDEX_OF_KEY = 0;
        final String key = StringUtils.trim(splitFields[INDEX_OF_KEY]);

        return EntityHeaderFields.valueOfName(key);
    }

    private String parseKey(final String[] splitFields) {
        final int INDEX_OF_KEY = 0;
        return StringUtils.trim(splitFields[INDEX_OF_KEY]);
    }

    private String parseValue(final String[] splitFields) {
        final int INDEX_OF_VALUE = 1;

        return StringUtils.trim(splitFields[INDEX_OF_VALUE]);
    }
}
