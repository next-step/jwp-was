package actions;

import webserver.http.request.HttpMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ActionFactory {
    enum DefaultActionCreator implements ActionCreator {
        CREATE_USER(HttpMethod.POST, "/user/create") {
            @Override
            public Action creater(HttpMethod method, String path) {
                return new CreateUserAction();
            }
        },
        GET_USER_LIST(HttpMethod.GET, "/user/list") {
            @Override
            public Action creater(HttpMethod method, String path) {
                return new UserListAction();
            }
        },
        LOGIN(HttpMethod.POST, "/user/login") {
            @Override
            public Action creater(HttpMethod method, String path) {
                return new LoginAction();
            }
        };

        private final HttpMethod method;
        private final String path;

        DefaultActionCreator(HttpMethod method, String path) {
            this.method = method;
            this.path = path;
        }

        private boolean isMatch(HttpMethod otherMethod, String otherPath) {
            return this.method == otherMethod && this.path.equals(otherPath);
        }

        public static Action lookupBy(HttpMethod method, String path) {
            return Arrays.stream(DefaultActionCreator.values())
                    .filter(action -> action.isMatch(method, path))
                    .map(creator -> creator.creater(method, path))
                    .findFirst()
                    .orElse(null);
        }
    }


    public static Action getInstance(HttpMethod method, String path) {
        return DefaultActionCreator.lookupBy(method, path);
    }
}
