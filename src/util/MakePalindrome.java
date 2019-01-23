package util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MakePalindrome {

    private static long extendFile(String filename) throws IOException {
        RandomAccessFile extender = new RandomAccessFile(filename, "rw");
        long originalLength = extender.length() - 1;
        extender.setLength(originalLength * 2 + 1);
        extender.close();
        return originalLength;
    }

    public static void main(String[] args) throws Exception {
        String filename = args[0];
        long originalLength = extendFile(filename);
        RandomAccessFile reader = new RandomAccessFile(filename, "r");
        RandomAccessFile writer = new RandomAccessFile(filename, "rw");
        writer.seek(writer.length() - 1);
        writer.writeByte('_');
        writer.seek(writer.length() - 2);

        for (int i = 0; i < originalLength; i++) {
            byte b = reader.readByte();
            writer.writeByte(b);
            writer.seek(writer.getFilePointer() - 2);
        }

        writer.close();
        reader.close();
    }
}
