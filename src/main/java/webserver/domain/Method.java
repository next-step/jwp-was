package webserver.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Method {

    private static final String BLANK = " ";

    private static final int METHOD_PLACE_IDX = 0;

    private String method;

    public Method(String method) {
        this.method = method;
    }

    public static Method parse(String requestLine) {
        String[] values = requestLine.split(BLANK);
        return new Method(values[METHOD_PLACE_IDX]);
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
