package webserver.http.domain.request.fixture;

import org.assertj.core.util.Lists;
import webserver.http.domain.request.Parameters;
import webserver.http.domain.request.URI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URIFixture {
    public static URI fixtureWithQueryParameters(String path, String key, String... values) {
        Map<String, List<String>> keyValues = new HashMap<>();
        keyValues.put(key, Lists.list(values));
        return new URI(path, new Parameters(keyValues));
    }
}
