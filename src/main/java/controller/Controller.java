package controller;

import http.request.Request;
import http.response.Response;

public interface Controller {
    void handleRequest(Request request, Response response);
}
