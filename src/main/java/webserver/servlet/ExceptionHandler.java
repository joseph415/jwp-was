package webserver.servlet;

import static controller.PageController.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import exception.NotFoundController;
import exception.RequestHandleException;
import http.HttpRequest;
import http.response.ContentType;
import http.response.HttpResponse;
import http.response.HttpStatus;
import utils.FileIoUtils;

public class ExceptionHandler {
    private static final Map<Class<? extends RuntimeException>, String> errorMapping;

    static {
        errorMapping = new HashMap<>();
        errorMapping.put(NotFoundController.class, "/error/notFound.html");
        errorMapping.put(RequestHandleException.class, "/error/internalServerError.html");
    }

    public static void handleError(RuntimeException runtimeException, HttpRequest httpRequest,
            HttpResponse httpResponse) {
        try {
            final String path = errorMapping.get(runtimeException.getClass());
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + path);

            setStatusLine(runtimeException, httpRequest, httpResponse);
            httpResponse.setBody(body, ContentType.HTML.getContentType());

        } catch (IOException | URISyntaxException e) {
            throw new RequestHandleException(e);
        }
    }

    private static void setStatusLine(RuntimeException runtimeException, HttpRequest httpRequest,
            HttpResponse httpResponse) {

        if(runtimeException instanceof NotFoundController) {
            httpResponse.setStatusLine(httpRequest.getHttpVersion(), HttpStatus.NOT_FOUND);
        }
        httpResponse.setStatusLine(httpRequest.getHttpVersion(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
