package endpoint;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EndPointHandlerScannerTest {

    @Test
    void scan_only_get_not_emptyPath_EndPointHandler() {
        HttpRequestEndpointHandlers endpointHandlers = EndPointHandlerScanner.scan();

        assertThat(endpointHandlers.stream().noneMatch(a -> a.endpoint.isEmptyPath())).isTrue();
    }
}
