package net.insprill.fetch4j.util;

import lombok.experimental.UtilityClass;

/**
 * A utility class for performing operations on headers.<br>
 * <b>WARNING:</b> This class is not part of the API and breaking changes may occur here at any time.
 */
@UtilityClass
public class HeaderUtil {

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
