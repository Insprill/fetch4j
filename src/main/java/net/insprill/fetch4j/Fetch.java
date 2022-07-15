package net.insprill.fetch4j;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.insprill.fetch4j.exception.HostNotFoundException;
import net.insprill.fetch4j.exception.InvalidURLException;
import net.insprill.fetch4j.exception.TimeoutException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

/**
 * CLass used to perform fetch operations.
 */
@UtilityClass
public class Fetch {

    /**
     * The default charset used when others are not specified.
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Performs a fetch operation with default parameters.
     *
     * @param url URL to fetch.
     * @return A {@link Response}.
     * @throws InvalidURLException   If the URL provided is malformed.
     * @throws TimeoutException      If the request times out.
     * @throws HostNotFoundException If the requested host is not found.
     */
    public Response fetch(String url) {
        return fetch(url, new Params());
    }

    /**
     * Performs a fetch operation.
     *
     * @param url    URL to fetch.
     * @param params Parameters to include in the request.
     * @return A {@link Response}.
     * @throws InvalidURLException   If the URL provided is malformed.
     * @throws TimeoutException      If the request times out.
     * @throws HostNotFoundException If the requested host is not found.
     */
    @SneakyThrows
    public Response fetch(String url, Params params) {
        if (!params.getQueries().isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", url + "?", "");
            for (Map.Entry<String, Object> entry : params.getQueries().entrySet()) {
                String param = entry.getKey() + "=" + URLEncoder.encode(String.valueOf(entry.getValue()));
                joiner.add(param);
            }
            url = joiner.toString();
        }
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new InvalidURLException(e);
        }
        for (Map.Entry<String, String> header : params.getHeaders().entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue());
        }
        conn.setRequestMethod(params.getMethod().name());
        conn.setInstanceFollowRedirects(params.isFollowRedirects());
        conn.setConnectTimeout(params.getConnectionTimeout());
        conn.setReadTimeout(params.getReadTimeout());
        conn.setUseCaches(params.isUseCaches());
        try {
            if (params.getBody() != null) {
                conn.setDoOutput(true);
                conn.getOutputStream().write(params.getBody());
            }
            conn.connect(); // Connect if we don't have a body to send. Won't do anything if already connected.
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e);
        } catch (IOException e) {
            throw new HostNotFoundException(e);
        }
        return new Response(conn);
    }

}
