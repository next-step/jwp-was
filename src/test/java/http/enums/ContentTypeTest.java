package http.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created By kjs4395 on 2020-06-09
 */
public class ContentTypeTest {

    @Test
    @DisplayName("request path 별 의도한 대로 타입 가져오는지 테스트")
    void getContentTypeTest() {

        assertEquals(ContentType.HTML, ContentType.findContentType("/user/create.html?id"));
        assertEquals(ContentType.CSS, ContentType.findContentType("/css/"));
        assertEquals(ContentType.JS, ContentType.findContentType("/js"));
        assertEquals(ContentType.NONE, ContentType.findContentType("/user/create"));

    }
}
