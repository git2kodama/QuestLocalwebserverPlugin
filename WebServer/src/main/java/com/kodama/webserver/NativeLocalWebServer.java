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

    //今回はファイルを返す静的用途
    @Override
    public Response serve(IHTTPSession session) {
        String _mime;
        Response.Status _status;
        // Mime
        _mime = this.get_mime(session.getUri());

        // ステータス
        _status = this.get_status(_mime);

        // ファイルパス取得

        // ファイル読み込み

        return newFixedLengthResponse("<html><h1>"+_mime+"</h1><h1>"+_status+"</h1><h1>"+session.getUri()+"</h1></html>");
    }
    // Mimeからステータスを抽出する
    private  Response.Status get_status(String mime) {
        Response.Status _status;

        _status = Response.Status.OK;

        if(mime.endsWith("plain")) {
            _status = Response.Status.NOT_FOUND;
        }

        return _status;
    }

    // URLからMimeを抽出する。
    private String get_mime(String url) {
        String _mime;

        if("\\".equals(url)) {
            _mime = ".html";
        }

        if (url.endsWith(".ico")) {
            _mime = "image/x-icon";
        } else if (url.endsWith(".png") || url.endsWith(".PNG")) {
            _mime = "image/png";
        } else if (url.endsWith(".jpg") || url.endsWith(".JPG") || url.endsWith(".jpeg") || url.endsWith(".JPEG")) {
            _mime = "image/jpeg";
        } else if (url.endsWith(".js")) {
            _mime = "application/javascript";
        } else if (url.endsWith(".css")) {
            _mime = "text/css";
        } else if (url.endsWith(".html") || url.endsWith(".htm")) {
            _mime = "text/html";
        } else if (url.endsWith(".map")) {
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
