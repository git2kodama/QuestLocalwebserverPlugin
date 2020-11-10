package com.kodama.webserver;

import com.unity3d.player.UnityPlayer;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class NativeLocalWebServer extends NanoHTTPD {

    public NativeLocalWebServer() throws IOException {
        super(2921);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        debug_message("Running! Point your browsers to http://localhost:8080/ \n");
    }

    public void debug_message(String _message){
        UnityPlayer.UnitySendMessage("webserver","android_debug_log", _message);
    }

}
