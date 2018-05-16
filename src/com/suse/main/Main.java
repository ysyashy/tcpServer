package com.suse.main;

import com.suse.server.TcpServer;

public class Main {
    public static void main(String []args){
        TcpServer server = new TcpServer();
        server.doService();
    }
}
