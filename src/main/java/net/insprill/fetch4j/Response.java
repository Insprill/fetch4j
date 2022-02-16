package net.insprill.fetch4j;

import lombok.SneakyThrows;
import net.insprill.fetch4j.util.StreamUtils;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class Response {

    private final HttpURLConnection conn;
    private String responseBody = null;

    protected Response(HttpURLConnection conn) {
        this.conn = conn;
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
        for (String s : contentType.split(";")) {
            String[] data = s.trim().split("=");
            if (data[0].equalsIgnoreCase("charset"))
                return data[1];
        }
        return null;
    }

    /**
     * Gets the content length from the {@code content-length} header, or -1 if unknown.
     *
     * @return The content length.
     */
    public long getContentLength() {
        return conn.getContentLengthLong();
    }

    /**
     * Gets the response body, decoding it with the encoding returned from the server,
     * or UTF-8 if none or an invalid one was provided.
     *
     * @return The response body.
     */
    @SneakyThrows
    public String getBody() {
        String encoding = (getContentEncoding() != null && Charset.isSupported(getContentEncoding())) ? getContentEncoding() : "UTF-8";
        return getBody(Charset.forName(encoding));
    }

    /**
     * Gets the response body.
     *
     * @param charset Charset to decode response body with.
     * @return The response body.
     */
    @SneakyThrows
    public String getBody(Charset charset) {
        if (responseBody != null) {
            return responseBody;
        } else {
            return responseBody = StreamUtils.inputStreamToString((getStatus() >= 400) ? conn.getErrorStream() : conn.getInputStream(), charset);
        }
    }

    @Override
    public String toString() {
        return "Response={" +
                "headers=" + conn.getHeaderFields() +
                ", body=" + getBody() +
                "}";
    }

}
