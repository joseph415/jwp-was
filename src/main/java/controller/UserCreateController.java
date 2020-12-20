package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.ContentType;
import http.response.HttpResponse;
import model.User;
import utils.StringUtils;

public class UserCreateController implements Controller {

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        createUser(httpRequest);
        httpResponse.redirect(httpRequest.getHttpVersion(), "/index.html",
                ContentType.HTML.getContentType());
    }

    private void createUser(HttpRequest request) {
        if (request.isPost()) {
            DataBase.addUser(User.from(StringUtils.readParameters(request.getBody())));
            return;
        }
        DataBase.addUser(User.from(request.getParameters()));
    }
}
