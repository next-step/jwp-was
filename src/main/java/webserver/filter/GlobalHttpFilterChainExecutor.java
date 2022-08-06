package webserver.filter;

import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;

public class GlobalHttpFilterChainExecutor {
    private GlobalHttpFilterChainExecutor() {
    }

    public static HttpResponseMessage doAllFilterChain(HttpRequestMessage requestMessage, HttpResponseMessage responseMessage) {

        for (GlobalHttpFilter globalHttpFilter : GlobalHttpFilterRegistry.GLOBAL_HTTP_FILTERS) {
            responseMessage = globalHttpFilter.doFilter(requestMessage, responseMessage);
        }

        return responseMessage;
    }
}
