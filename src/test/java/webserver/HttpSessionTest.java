package webserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.session.HttpUUIDSession;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpSessionTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpSession.class);

    @Test
    void getId() {
        // given
        HttpSession session = HttpUUIDSession.of();

        // when
        String id = session.getId();
        logger.debug("UUID Session value : {}", id);

        // then
        assertThat(id).isNotNull();
    }
}