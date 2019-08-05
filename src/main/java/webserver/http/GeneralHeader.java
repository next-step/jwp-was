package webserver.http;

import exception.AlreadyExistsException;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import webserver.http.request.CacheRequestDirective;
import webserver.http.response.CacheResponseDirective;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class GeneralHeader {

    private static final String TOKEN = ":";
    private static final String CRLF = "\r\n";

    private final Map<GeneralHeaderFields, CacheDirective> generalHeaders = new HashMap<>();

    public GeneralHeader(final List<String> httpRequestHeaders) {
        if (CollectionUtils.isEmpty(httpRequestHeaders)) {
            return;
        }

        setGeneralHeaders(httpRequestHeaders);
    }

    public Optional<CacheDirective> get(GeneralHeaderFields field) {
        return Optional.ofNullable(generalHeaders.get(field));
    }

    public void put(GeneralHeaderFields key, CacheDirective value) {
        if (generalHeaders.containsKey(key)) {
            throw new AlreadyExistsException(String.format("이미 존재하는 필드 입니다. 입력값 : %s", key.getName()));
        }

        generalHeaders.put(key, value);
    }

    @Override
    public String toString() {
        return generalHeaders.entrySet()
                .stream()
                .map(header -> header.getKey().getName() + ": " + header.getValue().getDirective())
                .collect(Collectors.joining(CRLF));
    }

    private void setGeneralHeaders(final List<String> httpRequestHeaders) {
        httpRequestHeaders.stream()
                .filter(GeneralHeaderFields::contains)
                .forEach(field -> {
                    final String[] splitFields = splitField(field);
                    if (splitFields == null) {
                        return;
                    }

                    final GeneralHeaderFields key = parseKey(splitFields);
                    final CacheDirective value = parseValue(splitFields);

                    this.generalHeaders.put(key, value);
                });
    }

    private String[] splitField(final String field) {
        if (!StringUtils.contains(field, TOKEN)) {
            return null;
        }

        return field.split(TOKEN);
    }

    private GeneralHeaderFields parseKey(final String[] splitFields) {
        final int INDEX_OF_KEY = 0;
        final String key = StringUtils.trim(splitFields[INDEX_OF_KEY]);

        return GeneralHeaderFields.valueOfName(key);
    }

    private CacheDirective parseValue(final String[] splitFields) {
        final int INDEX_OF_VALUE = 1;
        final String value = splitFields[INDEX_OF_VALUE];

        if (CacheRequestDirective.contains(value)) {
            return CacheRequestDirective.valueOfDirective(value);
        }

        return CacheResponseDirective.valueOfDirective(value);
    }
}
