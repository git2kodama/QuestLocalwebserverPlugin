package com.kodama.webserver;
import android.util.Log;
import com.unity3d.player.UnityPlayer;

public class NativeLocalWebServer {
    public NativeLocalWebServer() {
        Log.i("Unity","ネィティブ");
    }

    public void debug_message(){
        UnityPlayer.UnitySendMessage("webserver", "", "");
    }



}
