package TestDouble;

import java.io.DataOutputStream;

import http.HttpHeaders;
import http.HttpVersion;
import http.response.Response;
import http.response.StatusLine;

public class HttpResponseStub implements Response {
    @Override
    public void setStatusLine(StatusLine statusLine) {

    }

    @Override
    public void setBody(byte[] body, String contentType) {

    }

    @Override
    public void redirect(HttpVersion httpVersion, String location, String contentType) {

    }

    @Override
    public void send(DataOutputStream dos) {

    }

    @Override
    public StatusLine getStatusLine() {
        return null;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return null;
    }

    @Override
    public byte[] getBody() {
        return "<h1>hi</h1>".getBytes();
    }
}
