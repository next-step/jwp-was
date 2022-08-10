package model;

import controller.ViewControllerTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseTest.class);

    @Test
    void 리다이렉트_응답() throws UnsupportedEncodingException {

        String location = "/";

        final HttpResponse response = HttpResponse.redirect(location);

        logger.debug("respnse: {}", response);
    }
}
