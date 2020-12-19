package http.response;

public enum HttpStatus {
    OK("OK", 200),
    CREATED("CREATED", 201),
    NO_CONTENT("NO CONTENT", 204),
    MOVED_PERMANENTLY("MOVED PERMANENTLY", 301),
    BAD_REQUEST("BAD REQUEST", 400),
    NOT_FOUND("NOT FOUND", 404),
    INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR", 500);

    private final String text;
    private final int code;

    HttpStatus(String text, int code) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public int getCode() {
        return code;
    }
}
