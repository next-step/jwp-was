package utils;

import endpoint.TemplateResource;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

class TemplateResourceLoaderTest {
    private static final Logger log = LoggerFactory.getLogger(TemplateResourceLoaderTest.class);

    @Test
    void getDynamicTemplatePage() {

        TemplateResource templateResource = TemplatePageLoader.getDynamicTemplatePage("/user/list.html", new HashMap<>() {{
            put("users", Arrays.asList(
                    new User("a", "b", "c", "d"), new User("a1", "b1", "c1", "d1")));
        }});

        log.debug("templatePage: {}", new String(templateResource.getFileBytes()));
    }
}
