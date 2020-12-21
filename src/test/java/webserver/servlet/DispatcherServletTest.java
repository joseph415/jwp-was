package webserver.servlet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import TestDouble.HttpRequestStub;
import TestDouble.HttpResponseStub;
import http.request.HttpRequest;
import http.request.Request;
import http.response.Response;

class DispatcherServletTest {
    @Test
    void shouldInitializeDispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        dispatcherServlet.init();

        assertThat(dispatcherServlet.getHandlerMapping()).isInstanceOf(HandlerMapping.class);
    }

    @Test
    void shouldDestroyDispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        dispatcherServlet.destroy();

        assertThat(dispatcherServlet.getHandlerMapping()).isEqualTo(null);
    }

    //verity
    @Test
    void shouldDoService() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.init();
        Request request = new HttpRequestStub();
        Response response = new HttpResponseStub();

        dispatcherServlet.doService(request,response);
    }
}