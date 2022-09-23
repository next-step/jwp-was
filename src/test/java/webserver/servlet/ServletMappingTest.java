package webserver.servlet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ServletMappingTest {
	@Test
	void mappingTest(){
		ServletMapping servletMapping = new ServletMapping();
		Servlet servlet = servletMapping.match("/user/create");
		assertEquals(servlet.getClass(), UserCreateServlet.class);
	}
}