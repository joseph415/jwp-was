package controller;

import db.DataBase;
import http.ContentType;
import http.request.Request;
import http.response.Response;
import model.User;
import utils.StringUtils;

public class UserCreateController implements Controller {

    @Override
    public void handleRequest(Request request, Response response) {
        createUser(request);
        response.redirect(request.getHttpVersion(), "/index.html",
                ContentType.HTML.getContentType());
    }

    private void createUser(Request request) {
        if (request.isPost()) {
            DataBase.addUser(User.from(StringUtils.readParameters(request.getBody())));
            return;
        }
        DataBase.addUser(User.from(request.getParameters()));
    }
}
