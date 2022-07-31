package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Json 타입의 body 정보 객체
 */
public class JsonView extends DefaultView {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);

    private final Object body;

    public JsonView(Object body) {
        super(null, null, null);
        this.body = body;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonView)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JsonView jsonView = (JsonView) o;
        return Objects.equals(body, jsonView.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), body);
    }

    @Override
    public String toString() {
        return "JsonView{" +
                "body=" + body +
                '}';
    }
}
