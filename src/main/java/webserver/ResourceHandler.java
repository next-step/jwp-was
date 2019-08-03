package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;

public class ResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private ResourceNameResolver resourceNameResolver;
    private ClassLoader classLoader;

    public ResourceHandler() {
        this.resourceNameResolver = new ResourceNameResolver();
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public String getContents(String path) {
        String name = resourceNameResolver.resolveName(path);
        URL resource = classLoader.getResource(name);
        try (Stream<String> lines = Files.lines(get(requireNonNull(resource).toURI()))){
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            logger.error("File I/O Fail", e);
        } catch (URISyntaxException e) {
            logger.error("URI parse fail", e);
        }

        throw new RuntimeException("Resource load failed");
    }
}
