package http.requestline.path;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Uri {

    private final String value;

    public Uri(String value) {
        this.value = value;
    }
}
