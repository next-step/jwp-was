package http;

import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

import static utils.StringConstant.AMPERSAND;
import static utils.StringConstant.EQUAL_SIGN;

public class RequestBody {
    private QueryMap queryMap;

    private RequestBody(QueryMap queryMap) {
        this.queryMap = queryMap;
    }

    @Nullable
    public static RequestBody from(@Nonnull String requestBodyString) {
        if (StringUtils.isEmpty(requestBodyString)) {
            return null;
        }

        String[] splitByAmpersand = requestBodyString.split(AMPERSAND);
        QueryMap queryMap = new QueryMap();
        Arrays.stream(splitByAmpersand)
                .filter(Objects::nonNull)
                .forEach(query -> {
                    String[] splitByEqualSign = query.split(EQUAL_SIGN);
                    if (splitByEqualSign.length < 2) {
                        return;
                    }

                    // 같은 파라미터가 여러개 넘어오면 리스트로 넘겨줘야 하나?
                    queryMap.put(splitByEqualSign[0], splitByEqualSign[1]);
                });

        return new RequestBody(queryMap);
    }

    public QueryMap getQueryMap() {
        return queryMap;
    }
}
