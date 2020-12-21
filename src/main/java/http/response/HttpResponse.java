package http.response;

import static http.HttpHeaders.*;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.RequestHandleException;
import http.HttpHeaders;
import http.HttpVersion;

public class HttpResponse implements Response {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private StatusLine statusLine;
    private HttpHeaders httpHeaders;
    private byte[] body;

    public HttpResponse() {
        this.statusLine = StatusLine.create();
        this.httpHeaders = HttpHeaders.create();
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        this.statusLine.updateStatusLine(statusLine);
    }

    @Override
    public void setBody(byte[] body, String contentType) {
        this.body = body;
        httpHeaders.addHeader(CONTENT_TYPE, contentType);
        httpHeaders.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    @Override
    public void redirect(HttpVersion httpVersion, String location, String contentType) {
        this.statusLine.updateStatusLine(
                StatusLine.from(httpVersion, HttpStatus.MOVED_PERMANENTLY));
        this.httpHeaders.addHeader(LOCATION, location);
        this.httpHeaders.addHeader(CONTENT_TYPE, contentType);
    }

    @Override
    public void send(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.toStatusLineFormat() + System.lineSeparator());
            dos.writeBytes(httpHeaders.toHttpHeaderStringFormat() + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RequestHandleException(e);
        }
    }

    @Override
    public StatusLine getStatusLine() {
        return statusLine;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }
    @Override
    public byte[] getBody() {
        return body;
    }
}
