package webserver.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;
import webserver.exception.StringEmptyException;

import java.util.Objects;

@Getter
public class Method {

    private static final int METHOD_PLACE_IDX = 0;

    private HttpMethod method;

    public Method(HttpMethod method) {
        this.method = method;
    }

    public static Method parse(String method) {
        validate(method);
        return new Method(HttpMethod.valueOf(method));
    }

    private static void validate(String method) {
        if (!StringUtils.hasText(method))
            throw new StringEmptyException("method is empty");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return Objects.equals(this.method, method.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method);
    }
}
