package http;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.Parameters;
import http.request.RequestLine;
import utils.IOUtils;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final String body;

    private HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, String body) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        String firstLine = bufferedReader.readLine();
        logger.debug(firstLine);
        RequestLine requestLine = RequestLine.from(firstLine);
        HttpHeaders httpHeaders = HttpHeaders.from(IOUtils.readHeaders(bufferedReader));

        String body = null;
        if (httpHeaders.contains(CONTENT_LENGTH)) {
            body = IOUtils.readData(bufferedReader,
                    Integer.parseInt(httpHeaders.getAttribute(CONTENT_LENGTH)));
        }

        return new HttpRequest(requestLine, httpHeaders, body);
    }

    public boolean isGet(){
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public String getAttribute(String attribute) {
        return httpHeaders.getAttribute(attribute);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getBody() {
        return body;
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public Parameters getParameters() {
        return requestLine.getParameters();
    }
}
