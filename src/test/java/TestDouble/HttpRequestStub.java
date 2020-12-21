package TestDouble;

import http.HttpVersion;
import http.request.Parameters;
import http.request.Request;

public class HttpRequestStub implements Request {
    @Override
    public boolean isGet() {
        return true;
    }

    @Override
    public boolean isPost() {
        return false;
    }

    @Override
    public String getAttribute(String attribute) {
        return null;
    }

    @Override
    public String getPath() {
        return "/index.html";
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public HttpVersion getHttpVersion() {
        return null;
    }

    @Override
    public Parameters getParameters() {
        return null;
    }
}
