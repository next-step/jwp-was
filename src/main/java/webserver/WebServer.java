package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actions.user.UserCreateAction;
import actions.user.UserListAction;
import actions.user.UserLoginAction;
import enums.HttpMethod;
import webserver.mapper.ActionRequestMapper;
import webserver.mapper.RedirectRequestMapper;
import webserver.mapper.RequestMappers;
import webserver.mapper.ResourceRequestMapper;
import webserver.mapper.TemplateRequestMapper;
import webserver.mapper.ViewActionRequestMapper;
import webserver.resolvers.body.BodyResolvers;
import webserver.resolvers.body.FormBodyResolver;
import webserver.resolvers.view.HandlebarViewResolver;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        RedirectRequestMapper rootRedirectRequestMapper = RedirectRequestMapper.of("/", "/index.html");
        ResourceRequestMapper resourceRequestMapper = new ResourceRequestMapper()
                .addResourceMapping("/css/.*", "./static" )
                .addResourceMapping("/fonts/.*", "./static" )
                .addResourceMapping("/images/.*", "./static" )
                .addResourceMapping("/js/.*", "./static" );
        TemplateRequestMapper templateRequestMapper = new TemplateRequestMapper("./templates");
        ActionRequestMapper userCreatectionRequestMapper = new ActionRequestMapper("/user/create", new UserCreateAction());
        ActionRequestMapper userLoginActionRequestMapper = new ActionRequestMapper("/user/login", new UserLoginAction());
        HandlebarViewResolver handlebarViewResolver = HandlebarViewResolver.of("/templates", ".html");
        ViewActionRequestMapper viewActionRequestMapper = new ViewActionRequestMapper(handlebarViewResolver, new ActionRequestMapper("/user/list", new UserListAction(), new HttpMethod[]{ HttpMethod.GET }));

        RequestMappers requestMappers = RequestMappers.of(rootRedirectRequestMapper
        		, resourceRequestMapper
                , templateRequestMapper
                , userCreatectionRequestMapper
                , userLoginActionRequestMapper
                , viewActionRequestMapper
        );

        BodyResolvers bodyResolvers = BodyResolvers.of(FormBodyResolver.getInstance());


        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
            	executorService.execute(new RequestHandler(connection, requestMappers, bodyResolvers));
            }
        }
    }
}
