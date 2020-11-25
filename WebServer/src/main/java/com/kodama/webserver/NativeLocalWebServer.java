package com.kodama.webserver;

import com.unity3d.player.UnityPlayer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;

public class NativeLocalWebServer extends NanoHTTPD {

    private InputStream m_file_stream;
    private int m_file_size;
    private final Object m_sync_token = new Object();

    public NativeLocalWebServer() throws IOException {
        super(2921);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        debug_message("Running! Point your browsers to http://localhost:2921/ \n");
    }

    synchronized void get_resorce_bytes(byte[] _data,int _size) throws IOException {
        if(m_file_stream != null) {
            m_file_stream.close();
        }
        debug_message("message");
        try {
            m_file_stream = new ByteArrayInputStream(_data);
        } catch (Exception e) {
            e.printStackTrace();
            debug_message("ByteArrayInputStream 失敗");
        }
        m_file_size = _size;
        debug_message("image size : "+_size);
        synchronized (m_sync_token) {
            m_sync_token.notify();
        }

    }

    //今回はファイルを返す静的用途
    @Override
    public Response serve(IHTTPSession session) {
        String _mime;
        Response.IStatus _status;
        // Mime
        _mime = this.get_mime(session.getUri());

        // ステータス
        _status = this.get_status(_mime);

        // ファイル読み込み
        UnityPlayer.UnitySendMessage("webserver","android_resource_send", session.getUri());
        synchronized (m_sync_token) {
            try {
                m_sync_token.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newFixedLengthResponse(_status,_mime,m_file_stream,m_file_size);
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

        if("/".equals(url)) {
            url = "/.html";
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
