package com.example.psique.Utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;

import androidx.core.app.NotificationCompat;

import com.example.psique.Models.UserModel;
import com.example.psique.R;
import com.example.psique.Services.MyFCMService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class Constants {
    public static final String API_ID = "21138022f3497590";
    public static final String REGION = "eu";
    public static final String AUTH_KEY = "a28bd659c03a35cf19efdc5df09b36bb37386ea5";
    public static final String GROUP_ID = "group_id";


    public static final String USER_REFERENCES = "People";
    public static final String CHAT_REFERENCE = "Chat";
    public static final String CHAT_LIST_REFERENCE = "ChatList";
    public static final String CHAT_DETAIL_REFERENCE = "Detail";
    public static final String NOTIF_TITLE = "Title";
    public static final String NOTIF_CONTENT = "Content";
    public static final String NOTIF_SENDER = "Sender";
    public static final String NOTIF_ROOM_ID = "Room id";
    public static String roomSelected = "";
    public static UserModel currentUser = new UserModel();
    public static UserModel chatUser = new UserModel();


    public static String generateChatRoomId(String string1, String string2) {
        if (string1.compareTo(string2) > 0)
            return new StringBuilder(string1).append(string2).toString();
        else if(string1.compareTo(string2) < 0)
            return new StringBuilder(string2).append(string1).toString();
        else return new StringBuilder("Error_Chat_Contigo_mismo")
                    .append(new Random().nextInt()).toString();
    }

    public static String getName(UserModel chatUser) {
        return new StringBuilder(chatUser.getFirstName())
                .append(" ")
                .append(chatUser.getLastName())
                .append(" ")
                .toString();
    }

    public static String getFileName(ContentResolver contentResolver, Uri fileUri) {
        String result= null;

        if(fileUri.getScheme().equals("content")){
            Cursor cursor = contentResolver.query(fileUri, null, null, null);
            try {
                if(cursor != null && cursor.moveToFirst())
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }finally {{
                cursor.close();
            }

            }
        }

        if(result == null){
            result = fileUri.getPath();
            int cut = result.lastIndexOf("/");
            if(cut != -1)
                result =  result.substring(cut+1);
        }

        return result;
    }

    public static void showNotification(Context context, int id,
                                        String title, String content,
                                        String sender, String roomId,
                                        Intent intent) {
        PendingIntent pendingIntent = null;
        if(intent != null)
            pendingIntent = PendingIntent.getActivity(context,
                    id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String NOTIFICATION_CHANNEL_ID="com.example.psique";

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        //comprobar varsión
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                            "Psique",
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Chat de Psique");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.MAGENTA);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});

            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.icon_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_baseline_image_24));

        if(pendingIntent != null)
            builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        
        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(sender) &&
                !Constants.roomSelected.equals(roomId))
    }
}
