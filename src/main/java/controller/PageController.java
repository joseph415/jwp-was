package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import exception.RequestHandleException;
import http.ContentType;
import http.request.Request;
import http.response.HttpStatus;
import http.response.Response;
import http.response.StatusLine;
import utils.FileIoUtils;
import webserver.StaticResource;

public class PageController implements Controller {
    public static final String TEMPLATE_PATH = "./templates";

    @Override
    public void handleRequest(Request request, Response response) {
        try {
            String requestPath = request.getPath();
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + requestPath);

            response.setStatusLine(
                    StatusLine.from(request.getHttpVersion(), HttpStatus.OK));
            response.setBody(body, ContentType.HTML.getContentType());
        } catch (IOException | URISyntaxException e) {
            throw new RequestHandleException(e);
        }
    }
}
