package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.RequestHandleException;
import http.HttpHeaders;
import http.HttpVersion;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private StatusLine statusLine;
    private HttpHeaders httpHeaders;
    private byte[] body;

    public HttpResponse() {
        this.statusLine = StatusLine.create();
        this.httpHeaders = HttpHeaders.create();
    }

    public void setStatusLine(HttpVersion httpVersion, HttpStatus httpStatus) {
        this.statusLine.updateStatusLine(StatusLine.from(httpVersion, httpStatus));
    }

    public void setBody(byte[] body, String contentType) {
        this.body = body;
        httpHeaders.addHeader("Content-Type", contentType);
        httpHeaders.addHeader("Content-Length", String.valueOf(body.length));
    }

    public void redirect(HttpVersion httpVersion, String location, String contentType) {
        this.statusLine.updateStatusLine(
                StatusLine.from(httpVersion, HttpStatus.MOVED_PERMANENTLY));
        this.httpHeaders.addHeader("Location", location);
        this.httpHeaders.addHeader("Content-Type", contentType);
    }

    public void send(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.toStatusLineFormat() + System.lineSeparator());
            dos.writeBytes(httpHeaders.toHttpHeaderStringFormat() + System.lineSeparator());
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RequestHandleException(e);
        }
    }
}
