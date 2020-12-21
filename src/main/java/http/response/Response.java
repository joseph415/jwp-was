package http.response;

import java.io.DataOutputStream;

import http.HttpHeaders;
import http.HttpVersion;

public interface Response {
    void setStatusLine(StatusLine statusLine);

    void setBody(byte[] body, String contentType);

    void redirect(HttpVersion httpVersion, String location, String contentType);

    void send(DataOutputStream dos);

    StatusLine getStatusLine();

    HttpHeaders getHttpHeaders();

    byte[] getBody();
}
