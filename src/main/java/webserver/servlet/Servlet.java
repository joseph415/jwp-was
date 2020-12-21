package webserver.servlet;

import http.request.Request;
import http.response.Response;

public abstract class Servlet {
    protected abstract void init();

    protected abstract void destroy();

    public void service(Request request, Response response){
        if(request.isGet()){
            doGet(request, response);
        }
        doPost(request, response);
    }

    private void doGet(Request request, Response response){
        doService(request, response);
    }
    private void doPost(Request request, Response response){
        doService(request, response);
    }

    protected abstract void doService(Request request, Response response);
}
