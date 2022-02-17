package net.insprill.fetch4j.exception;

/**
 * Thrown when an invalid method parameter is attempted to be set.
 */
public class InvalidMethodException extends FetchException {

    /**
     * Constructs a new fetch exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidMethodException(String message) {
        super(message);
    }

}
