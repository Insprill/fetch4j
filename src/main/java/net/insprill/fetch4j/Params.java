package net.insprill.fetch4j;

import lombok.Getter;
import net.insprill.fetch4j.exception.InvalidMethodException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
public class Params {

    public static Params params() {
        return new Params();
    }

    private final Map<String, String> headers = new HashMap<>();
    private String method = "GET";
    private boolean followRedirects = true;
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
     */
    public Params header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Convenience method to set the {@code Content-Type} header.
     *
     * @param contentType Content type.
     */
    public Params contentType(String contentType) {
        header("Content-Type", contentType);
        return this;
    }

    /**
     * Set's whether the connection should follow redirects.
     *
     * @param followRedirects Follow redirects.
     */
    public Params followRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * Sets the connection timeout.
     *
     * @param timeoutMillis Timeout in milliseconds.
     */
    public Params connectionTimeout(int timeoutMillis) {
        this.connectionTimeout = timeoutMillis;
        return this;
    }

    /**
     * Sets the read timeout.
     *
     * @param timeoutMillis Timeout in milliseconds.
     */
    public Params readTimeout(int timeoutMillis) {
        this.readTimeout = timeoutMillis;
        return this;
    }

    /**
     * Convenience method to set the connection and read timeouts.
     *
     * @param timeoutMillis Timeout in milliseconds.
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
     * @see #contentType(String)
     */
    public Params body(String body) {
        return body(body, StandardCharsets.UTF_8);
    }

    /**
     * Set's the request body.
     *
     * @param body    Request body.
     * @param charset Charset to encoding the body with.
     * @see #contentType(String)
     */
    public Params body(String body, Charset charset) {
        this.body = body.getBytes(charset);
        return this;
    }

}
