package net.insprill.fetch4j.util;

import lombok.experimental.UtilityClass;
import net.insprill.fetch4j.Fetch;
import net.insprill.fetch4j.Params;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.StringJoiner;

@UtilityClass
public class URLUtils {

    /**
     * Adds query parameters from a {@link Params} object to the provided URL.
     *
     * @param url    The URL to add query parameters to.
     * @param params The params containing the query parameters.
     * @return The URL with query parameters added.
     */
    public String addQueryParams(String url, Params params) throws UnsupportedEncodingException {
        if (params.getQueries().isEmpty())
            return url;
        String charset = Fetch.DEFAULT_CHARSET.name();
        if (params.getHeaders().containsKey("Content-Type")) {
            charset = getContentCharset(params.getHeaders().get("Content-Type"));
        }
        StringJoiner joiner = new StringJoiner("&", url + "?", "");
        for (Map.Entry<String, Object> entry : params.getQueries().entrySet()) {
            String param = entry.getKey() + "=" + URLEncoder.encode(String.valueOf(entry.getValue()), charset);
            joiner.add(param);
        }
        return joiner.toString();
    }

    /**
     * Get the charset from a Content-Type header.
     *
     * @param contentType The value of a Content-Type header.
     * @return The name of the charset, or null if none was found.
     */
    public String getContentCharset(String contentType) {
        for (String s : contentType.split(";")) {
            String[] data = s.trim().split("=");
            if (data[0].equalsIgnoreCase("charset"))
                return data[1];
        }
        return null;
    }

}
