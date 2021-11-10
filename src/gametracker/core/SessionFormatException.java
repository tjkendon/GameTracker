package gametracker.core;

import java.io.IOException;

public class SessionFormatException extends IOException {

    private final String expected;
    private final String actual;

    public SessionFormatException() {
        super("Session format not parsable");
        expected = "";
        actual = "";
    }

    public SessionFormatException(Throwable ex) {
        super("Session format not parsable", ex);
        expected = "";
        actual = "";
    }

    public SessionFormatException(String explanation) {
        super("Session format not parsable: " + explanation);
        expected = "";
        actual = "";
    }

    public SessionFormatException(String explanation, Throwable ex) {
        super("Session format not parsable" + explanation, ex);
        expected = "";
        actual = "";
    }

    public SessionFormatException(String expected, String actual) {
        super(String.format("Expected session format: %s, but found: %s"));
        this.expected = expected;
        this.actual = actual;
    }

    public SessionFormatException(String expected, String actual, Throwable cause) {
        super(String.format("Expected session format: %s, but found: %s"), cause);
        this.expected = expected;
        this.actual = actual;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }
}
