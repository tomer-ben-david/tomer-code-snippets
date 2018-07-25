package org.tomerbd.snippets.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class URLUtils {
    public static void main(String[] args) throws Exception {
        new URLUtils().urlToFile("http://opennlp.sourceforge.net/models-1.5/da-token.bin", "/home/itamar/tmp/da-token.bin");
    }

    public void urlToFile(String fromURL, String toFilePath) throws IOException {
        try (
        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(fromURL).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(toFilePath);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }

    }


}
