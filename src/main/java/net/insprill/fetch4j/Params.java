package net.insprill.fetch4j;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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

    @Setter
    private static boolean defaultFollowRedirects = true;
    @Setter
    private static boolean defaultUseCaches = true;
    @Setter
    private static int defaultConnectionTimeout = 60000;
    @Setter
    private static int defaultReadTimeout = 60000;
    @Setter
    @NonNull
    private static Map<String, String> defaultHeaders = new HashMap<>();

    /**
     * @return Convenience method for constructing a new parameter builder.
     */
    public static Params params() {
        return new Params();
    }

    private Method method = Method.GET;
    private final Map<String, String> headers = defaultHeaders;
    private boolean followRedirects = defaultFollowRedirects;
    private boolean useCaches = defaultUseCaches;
    private int connectionTimeout = defaultConnectionTimeout;
    private int readTimeout = defaultReadTimeout;
    private byte[] body;

    /**
     * Sets the request method. The default method is GET.
     *
     * @param method The request method.
     * @return The parameter builder.
     */
    public Params method(Method method) {
        this.method = method;
        return this;
    }

    /**
     * Sets the request method. The default method is GET.
     * <UL>
     * <LI>GET
     * <LI>POST
     * <LI>HEAD
     * <LI>OPTIONS
     * <LI>PUT
     * <LI>DELETE
     * <LI>TRACE
     * </UL> Subject to protocol restrictions.
     *
     * @param method The name of the request method.
     * @return The parameter builder.
     * @throws InvalidMethodException If the method provided is invalid.
     * @see Params#method(Method)
     * @deprecated in favor of {@link Params#method(Method)}. May be removed in the future.
     */
    @Deprecated
    public Params method(String method) {
        this.method = Method.fromName(method);
        if (this.method == null) {
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
     * Set's the request body to a String using UTF-8 encoding.
     *
     * @param body Request body.
     * @return The parameter builder.
     * @see #contentType(String)
     */
    public Params body(String body) {
        return body(body, Fetch.DEFAULT_CHARSET);
    }

    /**
     * Set's the request body to a String.
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

    /**
     * Set's the request body.
     *
     * @param body Request body.
     * @return The parameter builder.
     * @see #contentType(String)
     */
    public Params body(byte[] body) {
        this.body = body;
        return this;
    }

    /**
     * The method used when making a request.
     */
    public enum Method {
        /**
         * The GET method requests a representation of the specified resource. Requests using GET should only retrieve data.
         */
        GET,
        /**
         * The HEAD method asks for a response identical to a GET request, but without the response body.
         */
        HEAD,
        /**
         * The POST method submits an entity to the specified resource, often causing a change in state or side effects on the server.
         */
        POST,
        /**
         * The PUT method replaces all current representations of the target resource with the request payload.
         */
        PUT,
        /**
         * The DELETE method deletes the specified resource.
         */
        DELETE,
        /**
         * The OPTIONS method describes the communication options for the target resource.
         */
        OPTIONS,
        /**
         * The TRACE method performs a message loop-back test along the path to the target resource.
         */
        TRACE;

        /**
         * Attempts to match a RequestMethod from its name case insensitively.
         *
         * @param name Name of request method.
         * @return The matching RequestMethod, or null if no match was found.
         */
        public static Method fromName(String name) {
            try {
                return Method.valueOf(name.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                return null;
            }
        }

    }

}
