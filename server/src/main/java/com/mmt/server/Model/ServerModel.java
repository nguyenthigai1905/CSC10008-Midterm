package com.mmt.server.Model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public final class ServerModel {
    public static boolean isConnected = false;
    private static ServerSocket server;
    private static Socket client;
    private static ServerModel model;
    private static int port;
    private static String IP;

    private ServerModel() {
    }

    public static void create(int portNumber) {
        if (model == null) {
            // create model
            model = new ServerModel();

            // create port
            port = portNumber;

            try {
                // create IP
                InetAddress address = InetAddress.getLocalHost();
                IP = address.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            try {
                // create server
                server = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect() {
        try {
            // create client
            client = server.accept();
            // set connect status
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            // close client
            client.close();
            // close server
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ServerSocket getServer() {
        return server;
    }

    public static Socket getClient() {
        return client;
    }

    public static boolean isConnect() {
        return isConnected;
    }

    public static List<String> getInfo() {
        return Arrays.asList(IP, String.valueOf(port));
    }

}