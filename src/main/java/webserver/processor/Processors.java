package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

import java.util.Arrays;
import java.util.List;

public class Processors {
    private static final HttpResponse NOT_FOUND_404 = HttpResponse.of(StatusCode.NOT_FOUND);

    private static final List<Processor> processors = Arrays.asList(
            new ControllerProcessor(),
            new TemplateProcessor(),
            new ResourceProcessor()
    );

    public HttpResponse process(final HttpRequest httpRequest) {
        return processors.stream()
                .filter(processor -> processor.isMatch(httpRequest))
                .findFirst()
                .map(processor -> processor.process(httpRequest))
                .orElse(NOT_FOUND_404);
    }

}
