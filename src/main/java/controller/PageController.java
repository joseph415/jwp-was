package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import exception.RequestHandleException;
import http.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import utils.FileIoUtils;
import webserver.StaticResource;

public class PageController implements Controller {
    public static final String TEMPLATE_PATH = "./templates";

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try{
            String requestPath = httpRequest.getPath();
            String contentType = StaticResource.fromPath(requestPath).getContentType();
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + requestPath);

            httpResponse.setStatusLine(httpRequest.getHttpVersion(), HttpStatus.OK);
            httpResponse.setBody(body,contentType);
        } catch (IOException |URISyntaxException e) {
            throw new RequestHandleException(e);
        }
    }
}
