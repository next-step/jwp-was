package webserver;

import com.google.common.collect.Sets;
import utils.FileIoUtils;
import utils.FileUtils;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hspark on 2019-08-05.
 */
public class FileResolver {
    private List<FileResolverRegistration> fileResolverRegistrations = new ArrayList<>();
    private static final Set<String> TEMPLATE_SUFFIX = Sets.newHashSet(".html", ".ico");
    private static final String TEMPLATE_CLASS_PATH = "./templates";
    private static final Set<String> RESOURCE_SUFFIX = Sets.newHashSet(".css", ".js");
    private static final String RESOURCE_CLASS_PATH = "./static";

    public FileResolver() {
        FileResolverRegistration templateResolver = new FileResolverRegistration(TEMPLATE_SUFFIX, TEMPLATE_CLASS_PATH);
        FileResolverRegistration resourceResolver = new FileResolverRegistration(RESOURCE_SUFFIX, RESOURCE_CLASS_PATH);

        fileResolverRegistrations.add(templateResolver);
        fileResolverRegistrations.add(resourceResolver);
    }

    public byte[] resolve(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String fileExtension = FileUtils.getExtension(httpRequest.getPath());
        FileResolverRegistration fileResolverRegistration = fileResolverRegistrations.stream()
                .filter(it -> it.isTarget(fileExtension)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not Found Resolver, extension : " + fileExtension));
        return FileIoUtils.loadFileFromClasspath(fileResolverRegistration.resolve(httpRequest));
    }
}
