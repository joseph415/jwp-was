package webserver.servlet;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import controller.PageController;
import exception.NotFoundController;
import controller.UserCreateController;

public class HandlerMapping {
    private final Map<String, Controller> mapping;

    public HandlerMapping() {
        this.mapping = new HashMap<>();
        mapping.put("/user/create", new UserCreateController());
        mapping.put("/index.html", new PageController());
        mapping.put("/error/notFound.html", new PageController());
        mapping.put("/error/internalServerError.html", new PageController());
    }

    public Controller getServlet(String path) {
        if (path == null) {
            throw new NullPointerException("Request-path가 null입니다.");
        }
        if(mapping.containsKey(path)){
            throw new NotFoundController("컨트롤러가 존재하지 않습니다.");
        }
        return mapping.get(path);
    }
}
