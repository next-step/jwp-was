package webserver.filter;

import org.junit.jupiter.api.Test;

class GlobalHttpFilterRegistryTest {

    @Test
    void registerGlobalHttpFilter() {

        for (GlobalHttpFilter globalHttpFilter : GlobalHttpFilterRegistry.GLOBAL_HTTP_FILTERS) {
            System.out.println(globalHttpFilter.getClass().getSimpleName());
        }
    }
}
