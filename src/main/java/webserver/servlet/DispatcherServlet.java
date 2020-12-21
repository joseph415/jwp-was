package webserver.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import exception.NotFoundPathException;
import exception.RequestHandleException;
import http.request.Request;
import http.response.Response;

public class DispatcherServlet extends Servlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        logger.info("start initializing DispatcherServlet ");
        logger.info("initializing " + HandlerMapping.class.getName());
        this.handlerMapping = new HandlerMapping();
    }

    @Override
    public void destroy() {
        logger.info("start Destroying DispatcherServlet");
        logger.info("Destroying " + HandlerMapping.class.getName());
        handlerMapping = null;
    }

    @Override
    public void doService(Request request, Response response) {
        try{
            Controller controller = handlerMapping.getServlet(request.getPath());
            controller.handleRequest(request, response);
        }catch (NotFoundPathException | RequestHandleException e) {
            ExceptionHandler.handleError(e, request, response);
        }
    }

    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }
}
