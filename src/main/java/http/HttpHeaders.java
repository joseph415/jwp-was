package http;

import static java.util.stream.Collectors.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String DELIMITER = ": ";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> requestHeaders;

    private HttpHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public static HttpHeaders create(){
        return new HttpHeaders(new LinkedHashMap<>());
    }

    public static HttpHeaders from(List<String> requestHeaders) {
        return requestHeaders.stream()
                .map(line -> line.split(DELIMITER))
                .collect(
                        collectingAndThen(
                                toMap(
                                        token -> token[KEY],
                                        token -> token[VALUE]),
                                HttpHeaders::new)
                );
    }

    public String toHttpHeaderStringFormat() {
        return requestHeaders.entrySet().stream()
                .map(entry -> entry.getKey() + DELIMITER + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void addHeader(String name, String value) {
        requestHeaders.put(name, value);
    }

    public boolean contains(String attribute) {
        return requestHeaders.containsKey(attribute);
    }

    public String getAttribute(String attribute) {
        return requestHeaders.get(attribute);
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
