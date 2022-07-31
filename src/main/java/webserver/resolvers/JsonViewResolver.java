package webserver.resolvers;

import com.fasterxml.jackson.databind.ObjectMapper;
import webserver.domain.JsonView;
import webserver.exception.ViewResolveException;

import java.io.IOException;
import java.util.Objects;

public class JsonViewResolver implements Resolver {

    @Override
    public boolean support(Object obj) {
        return Objects.nonNull(obj) && obj instanceof JsonView;
    }

    @Override
    public String resolve(Object obj) {
        JsonView view = (JsonView) obj;

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(view);
        } catch (IOException ie) {
            throw new ViewResolveException(this.getClass(), ie.getMessage());
        }
    }
}
