package com.mmt.client.Model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientModel {
    private static ClientModel model;
    private static Socket socket;
    private static String IP;
    private static Integer port;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    private static BufferedInputStream bufferedInput;

    private ClientModel() {
    }

    public static void create(String serverIP, Integer serverPort) throws IOException {
        if (model == null) {
            model = new ClientModel();

            port = serverPort;
            IP = serverIP;

            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 5000);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedInput = new BufferedInputStream(socket.getInputStream());
        }
    }

    public static DataInputStream getInput() {
        return dataInputStream;
    }

    public static DataOutputStream getOutput() {
        return dataOutputStream;
    }

    public static BufferedInputStream getBufferedInput() {
        return bufferedInput;
    }

    public static Socket getSocket() {
        return socket;
    }
}