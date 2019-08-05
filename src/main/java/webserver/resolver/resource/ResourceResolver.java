package webserver.resolver.resource;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.FileUtils;
import webserver.request.HttpRequest;
import webserver.resolver.Resolver;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hspark on 2019-08-05.
 */
public class ResourceResolver implements Resolver {
    private static final Logger logger = LoggerFactory.getLogger(ResourceResolver.class);

    private static final Set<String> TEMPLATE_SUFFIX = Sets.newHashSet(".html", ".ico");
    private static final String TEMPLATE_CLASS_PATH = "./templates";
    private static final Set<String> RESOURCE_SUFFIX = Sets.newHashSet(".css", ".js", ".tff", ".woff");
    private static final String RESOURCE_CLASS_PATH = "./static";

    private List<ResourceResolverRegistration> resourceResolverRegistrations = new ArrayList<>();

    public ResourceResolver() {
        ResourceResolverRegistration templateResolver = new ResourceResolverRegistration(TEMPLATE_SUFFIX, TEMPLATE_CLASS_PATH);
        ResourceResolverRegistration resourceResolver = new ResourceResolverRegistration(RESOURCE_SUFFIX, RESOURCE_CLASS_PATH);

        resourceResolverRegistrations.add(templateResolver);
        resourceResolverRegistrations.add(resourceResolver);
    }

    public void resolve(HttpRequest httpRequest, HttpResponse httpResponse) {
        String fileExtension = FileUtils.getExtension(httpRequest.getPath());
        ResourceResolverRegistration resourceResolverRegistration = resourceResolverRegistrations.stream()
                .filter(it -> it.isTarget(fileExtension)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not Found Resolver, extension : " + fileExtension));
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resourceResolverRegistration.resolve(httpRequest));
            httpResponse.ok(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            httpResponse.notFound(e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            httpResponse.internalServerError(e);
        }
    }
}
