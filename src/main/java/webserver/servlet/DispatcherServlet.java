package webserver.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import http.HttpRequest;
import http.response.HttpResponse;

public class DispatcherServlet extends Servlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        logger.info("initializing DispatcherServlet ");
        logger.info("initializing " + HandlerMapping.class.getName());
        this.handlerMapping = new HandlerMapping();
    }

    @Override
    public void destroy() {
        logger.info("Destroying " + DispatcherServlet.class.getName());
        logger.info("Destroying " + HandlerMapping.class.getName());
        handlerMapping = null;
    }

    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        Controller servlet = handlerMapping.getServlet(request.getPath());
        servlet.handleRequest(request, response);
    }
}
