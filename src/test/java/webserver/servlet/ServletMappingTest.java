package webserver.servlet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ServletMappingTest {
	@Test
	void givenPathMappedServlet(){
		Servlet servlet = ServletMapping.match("/user/create");

		assertEquals(servlet.getClass(), UserCreateServlet.class);
	}
}