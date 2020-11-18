package com.kodama.quest_external_storage;
import com.unity3d.player.UnityPlayer;

public class QuestExternalStorage {



    public void debug_message(String _message){
        UnityPlayer.UnitySendMessage("webserver","android_debug_log", _message);
    }
}
