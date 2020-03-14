package com.tx.study.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Tx
 * @date 2020/3/14
 */
public class NioClientHandle implements Runnable {

    private String host;
    private int port;
    private volatile boolean started;
    private Selector selector;
    private SocketChannel socketChannel;

    public NioClientHandle(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            // 非阻塞
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.started = false;
    }

    public void run() {
        // 链接服务器
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        while (started) {
            try {
                // 阻塞轮询 有事件发生就往下走
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                // 必须迭代 应为处理完事件需要移除
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理链接
     *
     * @param key
     */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            // 处理链接事件
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    // 注册读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else System.exit(-1);
            }
            // 处理读
            if (key.isReadable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if (readBytes > 0) {
                    //TODO do my work
                    // 业务代码
                } else if (readBytes < -1) { //发生四次挥手 链接关闭
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    /**
     * 进行链接
     *
     * @throws IOException
     */
    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
        } else {
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
    }
}
