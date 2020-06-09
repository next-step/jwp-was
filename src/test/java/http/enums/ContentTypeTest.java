package http.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created By kjs4395 on 2020-06-09
 */
public class ContentTypeTest {

    @Test
    @DisplayName("request path 별 의도한 대로 타입 가져오는지 테스트")
    void getContentTypeTest() {

        assertTrue(ContentType.html.equals(ContentType.findContentType("/user/create.html?id")));
        assertTrue(ContentType.css.equals(ContentType.findContentType("/css/")));
        assertTrue(ContentType.js.equals(ContentType.findContentType("/js")));
        assertTrue(ContentType.NONE.equals(ContentType.findContentType("/user/create")));

    }
}
