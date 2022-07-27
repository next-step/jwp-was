package webserver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonView extends DefaultView {
    private static final Logger logger = LoggerFactory.getLogger(JsonView.class);

    private final Object body;

    public JsonView(Object body) {
        super(null, null, null);
        this.body = body;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(body);
        } catch (IOException ie) {
            logger.error(ie.getMessage());

            return STRING_EMPTY;
        }
    }
}
