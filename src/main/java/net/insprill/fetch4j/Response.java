package net.insprill.fetch4j;

import lombok.SneakyThrows;
import net.insprill.fetch4j.util.URLUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Class containing information about a response from a fetch operation.
 *
 * @see Fetch
 */
public class Response {

    private static final byte[] EMPTY_RESPONSE_BODY = new byte[0];

    private final HttpURLConnection conn;
    private final byte[] responseBody;

    /**
     * Constructs a new Response from a connected {@link HttpURLConnection}.
     *
     * @param conn Connection to server.
     */
    @SneakyThrows
    protected Response(HttpURLConnection conn) {
        this.conn = conn;
        try (InputStream body = (getStatus() >= 400) ? conn.getErrorStream() : conn.getInputStream()) {
            if (body == null) {
                responseBody = EMPTY_RESPONSE_BODY;
                return;
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[4096];
            while ((nRead = body.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            responseBody = buffer.toByteArray();
        }
    }

    /**
     * Checks if the request is OK (Status code 200-299).
     *
     * @return True if the request is OK, false otherwise.
     */
    public boolean ok() {
        return getStatus() >= 200 && getStatus() < 300;
    }

    /**
     * Gets the HTTP response code returned from the server.
     * From responses like:
     * <PRE>
     * HTTP/1.0 200 OK
     * HTTP/1.0 404 Not Found
     * </PRE>
     * This will return {@code 200} and {@code 400} respectively.
     *
     * @return The response message, or {@code null}
     */
    @SneakyThrows
    public int getStatus() {
        return conn.getResponseCode();
    }

    /**
     * Gets the HTTP response message returned along with the
     * response code from a server.  From responses like:
     * <PRE>
     * HTTP/1.0 200 OK
     * HTTP/1.0 404 Not Found
     * </PRE>
     * This will return "OK" and "Not Found" respectively.
     *
     * @return The response message, or {@code null}
     */
    @SneakyThrows
    public String getStatusText() {
        return conn.getResponseMessage();
    }

    /**
     * Checks if a response header exists.
     *
     * @param name Header name.
     * @return True if the header exists, false otherwise.
     */
    public boolean hasHeader(String name) {
        return getHeader(name) != null;
    }

    /**
     * Gets the value of a response header.
     *
     * @param name Key to get value from.
     * @return Value of a response header, or null if it doesn't exist.
     */
    public String getHeader(String name) {
        return conn.getHeaderField(name);
    }

    /**
     * Gets an unmodifiable Map of all response headers.
     * The Map keys are the names of the headers,
     * and the Map values are unmodifiable Lists that represent
     * the corresponding header values.
     *
     * @return a Map of header fields
     */
    public Map<String, List<String>> getHeaders() {
        return conn.getHeaderFields();
    }

    /**
     * Gets the response's content type.
     *
     * @return The content type.
     */
    public String getContentType() {
        return conn.getContentType().split(";")[0];
    }

    /**
     * Attempts to get the content encoding of the response.
     * Will first check the {@code content-encoding} header, and if not set
     * will check the {@code content-type} header for a {@code charset} parameter.
     *
     * @return The content encoding, or {@code nulls} if none could be found.
     */
    public String getContentEncoding() {
        String encoding = conn.getContentEncoding();
        if (encoding != null) {
            return encoding;
        }
        String contentType = conn.getContentType();
        if (contentType == null) {
            return Fetch.DEFAULT_CHARSET.displayName();
        }
        return URLUtils.getContentCharset(contentType);
    }

    /**
     * Gets the content length from the {@code content-length} header, or -1 if unknown.
     *
     * @return The content length.
     */
    public long getContentLength() {
        return conn.getContentLengthLong() == -1 ? responseBody.length : conn.getContentLengthLong();
    }

    /**
     * Gets the response body as a String, decoding it with the encoding returned from the server,
     * or UTF-8 if none or an invalid one was provided.
     *
     * @return The response body.
     */
    @SneakyThrows
    public String getBody() {
        String encoding = (getContentEncoding() != null && Charset.isSupported(getContentEncoding()))
                ? getContentEncoding()
                : Fetch.DEFAULT_CHARSET.name();
        return getBody(Charset.forName(encoding));
    }

    /**
     * Gets the response body as a String.
     *
     * @param charset Charset to decode response body with.
     * @return The response body.
     */
    @SneakyThrows
    public String getBody(Charset charset) {
        return new String(responseBody, charset);
    }

    /**
     * Gets the response body's bytes.
     *
     * @return The bytes making up the response body.
     */
    public byte[] getBodyBytes() {
        byte[] resBody = new byte[responseBody.length];
        System.arraycopy(responseBody, 0, resBody, 0, responseBody.length);
        return resBody;
    }

    /**
     * Forces the connection to the server to be terminated.
     * It is <b>NOT</b> required to call this method unless you absolutely need to.
     * When not called, the connection will be kept open according to keep-alive rules, so it can be
     * reused in the future. If it doesn't get reused when the keep-alive timeout is reached,
     * it will be automatically closed.
     */
    public void disconnect() {
        conn.disconnect();
    }

    @Override
    public String toString() {
        return "Response={" +
                "headers=" + conn.getHeaderFields() +
                ", body=" + getBody() +
                "}";
    }

}
