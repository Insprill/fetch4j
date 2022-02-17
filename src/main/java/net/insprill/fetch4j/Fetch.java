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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@UtilityClass
public class Fetch {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
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
     * @param url    URL to fetch.
     * @param params Parameters to include in the request.
     * @return A {@link Response}.
     * @throws InvalidURLException   If the URL provided is malformed.
     * @throws TimeoutException      If the request times out.
     * @throws HostNotFoundException If the requested host is not found.
     */
    @SneakyThrows
    public Response fetch(String url, Params params) {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new InvalidURLException(e);
        }
        for (Map.Entry<String, String> header : params.getHeaders().entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue());
        }
        conn.setRequestMethod(params.getMethod());
        conn.setInstanceFollowRedirects(params.isFollowRedirects());
        conn.setConnectTimeout(params.getConnectionTimeout());
        conn.setConnectTimeout(params.getConnectionTimeout());
        conn.setUseCaches(params.isUseCaches());
        if (params.getBody() != null) {
            conn.setDoOutput(true);
            conn.getOutputStream().write(params.getBody());
        }
        try {
            conn.connect();
        } catch (SocketTimeoutException e) {
            throw new TimeoutException(e);
        } catch (IOException e) {
            throw new HostNotFoundException(e);
        }
        return new Response(conn);
    }

}
