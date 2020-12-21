package webserver.servlet;

import static controller.PageController.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import exception.NotFoundController;
import exception.RequestHandleException;
import http.ContentType;
import http.request.Request;
import http.response.HttpStatus;
import http.response.Response;
import http.response.StatusLine;
import utils.FileIoUtils;

public class ExceptionHandler {
    private static final Map<Class<? extends RuntimeException>, String> errorMapping;

    static {
        errorMapping = new HashMap<>();
        errorMapping.put(NotFoundController.class, "/error/notFound.html");
        errorMapping.put(RequestHandleException.class, "/error/internalServerError.html");
    }

    public static void handleError(RuntimeException runtimeException, Request request,
            Response response) {
        try {
            final String path = errorMapping.get(runtimeException.getClass());
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + path);

            setStatusLine(runtimeException, request, response);
            response.setBody(body, ContentType.HTML.getContentType());

        } catch (IOException | URISyntaxException e) {
            throw new RequestHandleException(e);
        }
    }

    private static void setStatusLine(RuntimeException runtimeException, Request request,
            Response response) {

        if (runtimeException instanceof NotFoundController) {
            response.setStatusLine(
                    StatusLine.from(request.getHttpVersion(), HttpStatus.NOT_FOUND));
        }
        response.setStatusLine(
                StatusLine.from(request.getHttpVersion(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
