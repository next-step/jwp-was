package webserver.http.cookie;

import java.util.Optional;

public interface Cookies {

    Optional<String> getString(final String key);
    boolean getBoolean(final String key);
    boolean isEmpty();
}
