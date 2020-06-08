package session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저의 세션을 저장하고 있는 인메모리 저장소")
class InMemorySessionHolderTest {
    private static final InMemorySessionHolder SESSION_HOLDER = new InMemorySessionHolder();

    @Test
    void saveAndLoadSession() {
        HttpSession session = TestHttpSession.of("sessionId");

        SESSION_HOLDER.save("sessionId", session);

        HttpSession sessionFromStore = SESSION_HOLDER.load("sessionId");

        assertThat(session).isEqualTo(sessionFromStore);
    }

    private static class TestHttpSession implements HttpSession {
        private String sessionId;
        private Map<String, Object> attributes = new HashMap<>();

        private TestHttpSession() {}

        public static TestHttpSession of(final String sessionId) {
            TestHttpSession testHttpSession = new TestHttpSession();
            testHttpSession.sessionId = sessionId;

            return testHttpSession;
        }


        @Override
        public String getId() {
            return sessionId;
        }

        @Override
        public void setAttribute(final String name, final Object value) {
            attributes.put(name, value);
        }

        @Override
        public Object getAttribute(final String name) {
            return attributes.get(name);
        }

        @Override
        public void removeAttribute(final String name) {
            attributes.remove(name);
        }

        @Override
        public void invalidate() {
            attributes.clear();
        }
    }
}