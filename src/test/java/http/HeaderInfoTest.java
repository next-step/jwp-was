package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class HeaderInfoTest {
    // http request / header / contentbody 분리 하자 들어온거 분석해서
    private static final Logger log = LoggerFactory.getLogger(HeaderInfoTest.class);
    private Header headerInfo;

    @BeforeEach
    void createHeaderInfo() {
        headerInfo = new Header();
    }

    @Test
    void httpHeaderInfoAddTest() {
        headerInfo.addHeaderValue("Content-Length: 59");
        assertThat(headerInfo.getValue("Content-Length")).isEqualTo("59");
    }

    @Test
    void invalid() {
        assertThatThrownBy(() -> headerInfo.addHeaderValue("Content-Length"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void defaultReturnTest() {
        assertThat(headerInfo.getValue("test")).isEqualTo(StringUtils.EMPTY);
    }
}
