package webserver.mapper;

import webserver.processor.Processor;
import webserver.processor.ResourceProcessor;
import webserver.processor.TemplateProcessor;

import java.util.Arrays;
import java.util.List;

public class RequestProcessorMapper {
    List<Processor> processors = Arrays.asList(
            new TemplateProcessor(),
            new ResourceProcessor()
    );


}
