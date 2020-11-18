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
        String _mime;

        _mime = this.get_mime(session.getUri());

        return newFixedLengthResponse("<html><h1>"+_mime+"</h1></html>");
    }

    private String get_mime(String mime) {
        String _mime="/";

        if (mime.endsWith(".ico")) {
            _mime = "image/x-icon";
        } else if (mime.endsWith(".png") || mime.endsWith(".PNG")) {
            _mime = "image/png";
        } else if (mime.endsWith(".jpg") || mime.endsWith(".JPG") || mime.endsWith(".jpeg") || mime.endsWith(".JPEG")) {
            _mime = "image/jpeg";
        } else if (mime.endsWith(".js")) {
            _mime = "application/javascript";
        } else if (mime.endsWith(".css")) {
            _mime = "text/css";
        } else if (mime.endsWith(".html") || mime.endsWith(".htm")) {
            _mime = "text/html";
        } else if (mime.endsWith(".map")) {
            _mime = "application/json";
        } else {
            _mime = "text/plain";
        }

        return _mime;
    }

    public void debug_message(String _message){
        UnityPlayer.UnitySendMessage("webserver","android_debug_log", _message);
    }

}
