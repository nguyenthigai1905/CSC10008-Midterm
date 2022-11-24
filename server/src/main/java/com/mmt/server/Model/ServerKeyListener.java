package com.mmt.server.Model;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;

public class ServerKeyListener implements NativeKeyListener {

    public ServerKeyListener() {
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        try {
            String key = String.valueOf(e.getKeyChar());
            ServerHandlerModel.getOutput().writeUTF(key);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
