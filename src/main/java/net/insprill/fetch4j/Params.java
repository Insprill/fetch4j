package net.insprill.fetch4j;

import lombok.Getter;
import net.insprill.fetch4j.exception.InvalidMethodException;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Builder used to set parameters for fetch operations.
 */
@Getter
public class Params {

    /**
     * @return Convenience method for constructing a new parameter builder.
     */
    public static Params params() {
        return new Params();
    }

    private final Map<String, String> headers = new HashMap<>();
    private String method = "GET";
    private boolean followRedirects = true;
    private boolean useCaches = true;
    private int connectionTimeout = 60000;
    private int readTimeout = 60000;
    private byte[] body;

    /**
     * Sets the request method. Must be one of the following:
     * <UL>
     * <LI>GET
     * <LI>POST
     * <LI>HEAD
     * <LI>OPTIONS
     * <LI>PUT
     * <LI>DELETE
     * <LI>TRACE
     * </UL> subject to protocol restrictions.  The default
     * method is GET.
     *
     * @param method The HTTP method.
     * @return The parameter builder.
     * @throws InvalidMethodException If the method provided is invalid.
     */
    public Params method(String method) {
        method = method.toUpperCase(Locale.ROOT);
        switch (method) {
            case "GET":
            case "POST":
            case "HEAD":
            case "OPTIONS":
            case "PUT":
            case "DELETE":
            case "TRACE":
                this.method = method;
                break;
            default:
                throw new InvalidMethodException(method);
        }
        return this;
    }

    /**
     * Set's a request header.
     *
     * @param key   Header key.
     * @param value Header value.
     * @return The parameter builder.
     */
    public Params header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Convenience method to set the {@code Content-Type} header.
     *
     * @param contentType Content type.
     * @return The parameter builder.
     */
    public Params contentType(String contentType) {
        header("Content-Type", contentType);
        return this;
    }

    /**
     * Set's whether the connection should follow redirects.
     *
     * @param followRedirects Follow redirects.
     * @return The parameter builder.
     */
    public Params followRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * Sets whether to use caches for this request.
     * Sometimes it's important to be able to ignore the caches
     * (e.g., the "reload" button in a browser).
     * Defaults to true.
     *
     * @param useCaches Whether to use caches.
     * @return The parameter builder.
     */
    public Params useCaches(boolean useCaches) {
        this.useCaches = useCaches;
        return this;
    }

    /**
     * Sets the connection timeout.
     *
     * @param timeoutMillis Timeout in milliseconds.
     * @return The parameter builder.
     */
    public Params connectionTimeout(int timeoutMillis) {
        this.connectionTimeout = timeoutMillis;
        return this;
    }

    /**
     * Sets the read timeout.
     *
     * @param timeoutMillis Timeout in milliseconds.
     * @return The parameter builder.
     */
    public Params readTimeout(int timeoutMillis) {
        this.readTimeout = timeoutMillis;
        return this;
    }

    /**
     * Convenience method to set the connection and read timeouts.
     *
     * @param timeoutMillis Timeout in milliseconds.
     * @return The parameter builder.
     */
    public Params timeout(int timeoutMillis) {
        connectionTimeout(timeoutMillis);
        readTimeout(timeoutMillis);
        return this;
    }

    /**
     * Set's the request body using UTF-8 encoding.
     *
     * @param body Request body.
     * @return The parameter builder.
     * @see #contentType(String)
     */
    public Params body(String body) {
        return body(body, Fetch.DEFAULT_CHARSET);
    }

    /**
     * Set's the request body.
     *
     * @param body    Request body.
     * @param charset Charset to encoding the body with.
     * @return The parameter builder.
     * @see #contentType(String)
     */
    public Params body(String body, Charset charset) {
        this.body = body.getBytes(charset);
        return this;
    }

}
