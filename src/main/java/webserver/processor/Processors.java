package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;

import java.util.Arrays;
import java.util.List;

public class Processors {

    private static final List<Processor> processors = Arrays.asList(
            new TemplateProcessor(),
            new ResourceProcessor(),
            new ControllerProcessor()
    );

    public HttpResponse process(final HttpRequest httpRequest) {
        return processors.stream()
                .filter(processor -> processor.isMatch(httpRequest))
                .findFirst()
                .map(processor -> processor.process(httpRequest))
                .orElseThrow(IllegalArgumentException::new);
    }

}
