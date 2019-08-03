package webserver.resolvers.body;

import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BodyResolvers {

    List<BodyResolver> bodyResolvers;

    private BodyResolvers(List<BodyResolver> bodyResolvers) {
        this.bodyResolvers = bodyResolvers;
    }

    public static BodyResolvers of(BodyResolver... bodyResolvers) {
        return new BodyResolvers(Arrays.asList(bodyResolvers));
    }

    public HttpRequest resoveByMatchResolver(HttpRequest httpRequest) {
        return Optional.ofNullable(getMatchedBodyResolver(httpRequest))
                .map(resolver -> resolver.resolve(httpRequest))
                .orElse(httpRequest);
    }

    private BodyResolver getMatchedBodyResolver(HttpRequest httpRequest){
        return this.bodyResolvers.stream()
                .filter(resolver -> resolver.isMatchedContentType(httpRequest.getHeader(HttpHeaders.CONTENT_TYPE)))
                .findFirst()
                .orElse(null);
    }
}
