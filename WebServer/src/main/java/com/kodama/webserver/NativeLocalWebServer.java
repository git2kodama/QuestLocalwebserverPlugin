package com.kodama.webserver;

import com.unity3d.player.UnityPlayer;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class NativeLocalWebServer extends NanoHTTPD {

    private String _www_root="";

    public NativeLocalWebServer() throws IOException {
        super(2921);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        debug_message("Running! Point your browsers to http://localhost:2921/ \n");
    }

    public void set_www_root(String _path){
        _www_root = _path + "/www";
    }

    @Override
    public Response serve(IHTTPSession session) {
        return newFixedLengthResponse("<html><h1>これでOK</h1></html>");
    }

    public void debug_message(String _message){
        UnityPlayer.UnitySendMessage("webserver","android_debug_log", _message);
    }

}
