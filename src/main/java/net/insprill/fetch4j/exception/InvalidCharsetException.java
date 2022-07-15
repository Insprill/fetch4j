package net.insprill.fetch4j.exception;

/**
 * Thrown when an invalid charset is used.
 */
public class InvalidCharsetException extends FetchException {

    /**
     * Constructs a new fetch exception with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause The cause. (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     */
    public InvalidCharsetException(Throwable cause) {
        super(cause);
    }

}
