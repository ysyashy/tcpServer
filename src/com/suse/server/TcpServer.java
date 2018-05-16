package com.suse.server;

import com.suse.compress.QuickLZ;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.suse.compress.QuickLZ.compress;

public class TcpServer {
    private final int port = 9999;
    private byte[] datas = null;
    private ServerSocket serverSocket = null;

    public TcpServer(){
        datas = new byte[4096];
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doService() {
        while(true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                int res = 0;
                while(-1 != (res = clientSocket.getInputStream().read(datas))){
                    System.out.println(new String(datas, 0, res));
                    byte[] ret = QuickLZ.decompress(datas);
                    System.out.println(new String(ret));
                    byte[] bytes = QuickLZ.compress(ret, 1);
                    clientSocket.getOutputStream().write(bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(null != clientSocket) {
                    try { clientSocket.close(); } catch (IOException e) { e.printStackTrace(); }
                }
                clientSocket = null;
            }
        }
    }
}
