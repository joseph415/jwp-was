package http.request;

import http.HttpVersion;

public interface Request {
    boolean isGet();

    boolean isPost();

    String getAttribute(String attribute);

    String getPath();

    String getBody();

    HttpVersion getHttpVersion();

    Parameters getParameters();
}
