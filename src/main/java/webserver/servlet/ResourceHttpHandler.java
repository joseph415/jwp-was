package webserver.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import exception.NotFoundPathException;
import exception.RequestHandleException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import utils.FileIoUtils;
import webserver.StaticResource;

public class ResourceHttpHandler {
    public static void handleResource(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String requestPath = httpRequest.getPath();
            String contentType = StaticResource.fromPath(requestPath).getContentType();
            byte[] body = FileIoUtils.loadFileFromClasspath(
                    StaticResource.STATIC_PATH + requestPath);

            httpResponse.setStatusLine(StatusLine.from(httpRequest.getHttpVersion(), HttpStatus.OK));
            httpResponse.setBody(body, contentType);
        } catch (NoSuchElementException e) {
            throw new NotFoundPathException("404 Not found");
        } catch (IOException | URISyntaxException e) {
            throw new RequestHandleException(e);
        }
    }
}
