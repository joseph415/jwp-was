package webserver.servlet;

import http.HttpRequest;
import http.response.HttpResponse;

public abstract class Servlet {
    protected abstract void init();

    protected abstract void destroy();

    public void service(HttpRequest request, HttpResponse response){
        if(request.isGet()){
            doGet(request, response);
        }
        doPost(request, response);
    }

    void doGet(HttpRequest request, HttpResponse response){
        doService(request, response);
    }
    void doPost(HttpRequest request, HttpResponse response){
        doService(request, response);
    }

    protected abstract void doService(HttpRequest request, HttpResponse response);
}
