package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.HttpHeaders;
import http.HttpVersion;
import utils.IOUtils;

public class HttpRequest implements Request {
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

    public static Request from(BufferedReader bufferedReader) throws IOException {
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

    @Override
    public boolean isGet(){
        return requestLine.isGet();
    }

    @Override
    public boolean isPost() {
        return requestLine.isPost();
    }

    @Override
    public String getAttribute(String attribute) {
        return httpHeaders.getAttribute(attribute);
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    @Override
    public Parameters getParameters() {
        return requestLine.getParameters();
    }
}
