package com.tx.study.nio.simple;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/18 9:31
 */
public class FileChannelDemo {

    public static void main(String[] args) {
        try (RandomAccessFile aFile = new RandomAccessFile("D:\\test_pdf\\test_filen.txt", "rw")) {
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(256);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                System.out.println("Read " + bytesRead);
                buf.flip();
                System.out.println(new String(buf.array(), StandardCharsets.UTF_8));
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
