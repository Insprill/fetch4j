package net.insprill.fetch4j.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

@UtilityClass
public class StreamUtils {

    /**
     * Reads an {@link InputStream} and returns it in String form.
     *
     * @param stream  The InputStream to read.
     * @param charset The Charset to decode the stream with.
     * @return The String value of the InputStream, with lines seperated by a newline character ({@code \n}).
     */
    public String inputStreamToString(InputStream stream, Charset charset) {
        return new BufferedReader(new InputStreamReader(stream, charset))
                .lines()
                .collect(Collectors.joining("\n"));
    }

}
