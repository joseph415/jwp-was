package http.response;

import http.HttpVersion;

public class StatusLine {
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.OK;
    private static final HttpVersion STANDARDIZED_PROTOCOL_HTTP_VERSION = HttpVersion.VERSION_1_1;

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;

    private StatusLine(HttpVersion httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public static StatusLine create() {
        return new StatusLine(STANDARDIZED_PROTOCOL_HTTP_VERSION, DEFAULT_HTTP_STATUS);
    }

    public static StatusLine from(HttpVersion httpVersion, HttpStatus httpStatus){
        return new StatusLine(httpVersion, httpStatus);
    }

    public void updateStatusLine(StatusLine statusLine){
        this.httpVersion = statusLine.httpVersion;
        this.httpStatus = statusLine.httpStatus;
    }

    public String toStatusLineFormat() {
        return String.format("%s %d %s %s", httpVersion.getHttpVersion(),
                httpStatus.getCode(), httpStatus.getText(), System.lineSeparator());
    }
}
