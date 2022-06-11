package com.example.psique.Services;


import androidx.annotation.NonNull;

import com.example.psique.Utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

/**
 * Cuerpo de la notificación al recibirla
 */
public class MyFCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Map<String, String> dataRec = message.getData();
        if(dataRec != null){
            Constants.showNotification(this, new Random().nextInt(),
                    dataRec.get(Constants.NOTIF_TITLE),
                    dataRec.get(Constants.NOTIF_CONTENT),
                    dataRec.get(Constants.NOTIF_SENDER),
                    dataRec.get(Constants.NOTIF_ROOM_ID),
                    null);
        }

    }
}