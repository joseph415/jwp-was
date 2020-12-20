package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse);
}
