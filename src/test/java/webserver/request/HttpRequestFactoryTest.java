package webserver.request;

import org.junit.jupiter.api.Test;
import support.TestHttpMessageLoader;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by hspark on 2019-08-11.
 */
class HttpRequestFactoryTest {


    @Test
    public void request_GET() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_GET_index.txt");
        HttpRequest request = HttpRequestFactory.create(in);

        assertTrue(request.getHttpMethod().isGet());
        assertEquals("/index.html", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
    }

    @Test
    public void request_GET2() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_GET_signUp.txt");
        HttpRequest request = HttpRequestFactory.create(in);

        assertTrue(request.getHttpMethod().isGet());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("test", request.getParameter("userId"));
        assertEquals("test", request.getParameter("password"));
    }


    @Test
    public void request_POST() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_POST.txt");
        HttpRequest request = HttpRequestFactory.create(in);

        assertTrue(request.getHttpMethod().isPost());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }


    @Test
    public void request_POST2() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_POST2.txt");
        HttpRequest request = HttpRequestFactory.create(in);

        assertTrue(request.getHttpMethod().isPost());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getParameter("id"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}