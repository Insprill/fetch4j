package net.insprill.fetch4j.util;

import lombok.experimental.UtilityClass;
import net.insprill.fetch4j.Fetch;
import net.insprill.fetch4j.Params;
import net.insprill.fetch4j.exception.InvalidCharsetException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.StringJoiner;

/**
 * A utility class for performing various URL related operations.
 * This class is not part of the API and breaking changes may occur here at any time.
 */
@UtilityClass
public class URLUtils {

    /**
     * Adds query parameters from a {@link Params} object to the provided URL.
     *
     * @param url    The URL to add query parameters to.
     * @param params The params containing the query parameters.
     * @return The URL with query parameters added.
     * @throws InvalidCharsetException If an invalid Content-Type header charset was used.
     */
    public String addQueryParams(String url, Params params) throws InvalidCharsetException {
        if (params.getQueries().isEmpty())
            return url;
        String charset = Fetch.DEFAULT_CHARSET.name();
        if (params.getHeaders().containsKey("Content-Type")) {
            String contentCharset = HeaderUtil.getContentCharset(params.getHeaders().get("Content-Type"));
            if (contentCharset != null) {
                charset = contentCharset;
            }
        }
        StringJoiner joiner = new StringJoiner("&", url + "?", "");
        try {
            for (Map.Entry<String, Object> entry : params.getQueries().entrySet()) {
                String param = entry.getKey() + "=" + URLEncoder.encode(String.valueOf(entry.getValue()), charset);
                joiner.add(param);
            }
        } catch (UnsupportedEncodingException e) {
            throw new InvalidCharsetException(e);
        }
        return joiner.toString();
    }

}
