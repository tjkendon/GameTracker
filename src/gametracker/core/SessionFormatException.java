package gametracker.core;

import java.io.IOException;

/**
 * Exception thrown when play session data cannot be parsed from a line.
 */
public class SessionFormatException extends IOException {

    /**
     * The format we expected the line to be in.
     */
    private final String expected;

    /**
     * The format the line was in.
     */
    private final String actual;

    /**
     *
     * Creates a new exception with a default error message.
     *
     */
    public SessionFormatException() {
        super("Session format not parsable");
        expected = "";
        actual = "";
    }

    /**
     *
     * Creates a new exception with a default error message and the given
     * throwable as a cause.
     *
     * @param ex the causing exception
     */
    public SessionFormatException(Throwable ex) {
        super("Session format not parsable", ex);
        expected = "";
        actual = "";
    }

    /**
     *
     * Creates a new exception with the provided explanation
     *
     * @param explanation a text explanation of the parse problem
     */
    public SessionFormatException(String explanation) {
        super("Session format not parsable: " + explanation);
        expected = "";
        actual = "";
    }

    /**
     *
     * Creates a new exception with the provided explanation and the given cause.
     *
     * @param explanation a text explanation of the parse problem
     * @param ex the causing exception
     */
    public SessionFormatException(String explanation, Throwable ex) {
        super("Session format not parsable" + explanation, ex);
        expected = "";
        actual = "";
    }

    /**
     *
     * Creates a new exception with the expected format and actual line.
     *
     * @param expected the format that was expected
     * @param actual the actual value found
     */
    public SessionFormatException(String expected, String actual) {
        super(String.format("Expected session format: %s, but found: %s"));
        this.expected = expected;
        this.actual = actual;
    }

    /**
     *
     * Creates a new exception with the expected format and actual line and the
     * throwable that caused the error.
     *
     * @param expected the format that was expected
     * @param actual the actual value found
     * @param cause the causing exception
     */
    public SessionFormatException(String expected, String actual, Throwable cause) {
        super(String.format("Expected session format: %s, but found: %s"), cause);
        this.expected = expected;
        this.actual = actual;
    }

    /**
     *
     * Returns the format that was expected, if it has been provided
     *
     * @return the expected format or null
     */
    public String getExpected() {
        return expected;
    }

    /**
     *
     * Returns the line that was found, if it has been provided.
     *
     * @return the found line or null
     */
    public String getActual() {
        return actual;
    }
}
