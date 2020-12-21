package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import exception.NotFoundPathException;
import exception.RequestHandleException;
import http.request.Request;
import http.response.HttpStatus;
import http.response.Response;
import http.response.StatusLine;
import utils.FileIoUtils;
import webserver.StaticResource;

public class ResourceHttpHandler {
    public static void handleResource(Request request, Response response) {
        try {
            String requestPath = request.getPath();
            String contentType = StaticResource.fromPath(requestPath).getContentType();
            byte[] body = FileIoUtils.loadFileFromClasspath(
                    StaticResource.STATIC_PATH + requestPath);

            response.setStatusLine(StatusLine.from(request.getHttpVersion(), HttpStatus.OK));
            response.setBody(body, contentType);
        } catch (NoSuchElementException e) {
            throw new NotFoundPathException("404 Not found");
        } catch (IOException | URISyntaxException e) {
            throw new RequestHandleException(e);
        }
    }
}
