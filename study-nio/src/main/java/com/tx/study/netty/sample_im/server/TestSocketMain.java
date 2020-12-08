package com.tx.study.netty.sample_im.server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/27 10:45
 */
public class TestSocketMain {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Executors.newSingleThreadExecutor().execute(() -> {
                byte[] buff = new byte[256];
                int i = 0;
                while (true) {
                    try {
                        if (!((i = is.read(buff)) != -1)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(new String(buff, 0, i).replaceAll("\n", ""));
                }
            });
            while (true) {
                String line = in.readLine();
                os.write((line + "\n").getBytes());
                os.flush();
                if (line.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
