package com.example.psique.Utils;

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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

/**
 * En esta clase se definen constantes o métodos comunes
 * que serán usadas en distintas clases
 */
public class Constants {
    public static final String USER_REFERENCES = "People";//referencia a la clase People de la base de datos (Usuarios)
    public static final String CHAT_REFERENCE = "Chat";//referencia a la clase Chat de la base de datos
    public static final String CHAT_LIST_REFERENCE = "ChatList";//referencia a ChatList de la base de datos (lista de chats)
    public static final String CHAT_DETAIL_REFERENCE = "Detail";//detalles del mensaje, objeto de la base de datos

    public static final String NOTIF_TITLE = "Title";//título de la notificación
    public static final String NOTIF_CONTENT = "Content";//contenido de la notificaión
    public static final String NOTIF_SENDER = "Sender";//persona que la envía
    public static final String NOTIF_ROOM_ID = "Room id";//id del chat al qu se envía
    public static String roomSelected = "";//si hay un chat seleccionado

    public static UserModel currentUser = new UserModel();//usuario actual
    public static UserModel chatUser = new UserModel();//usuario del chat

    /**
     * Método que genera un id al chat
     * @param string1
     * @param string2
     * @return
     */
    public static String generateChatRoomId(String string1, String string2) {
        if (string1.compareTo(string2) > 0)//si la primera cadena es léxicamente mayor crea el id poniendo a éste primero
            return new StringBuilder(string1).append(string2).toString();
        else if(string1.compareTo(string2) < 0)//si la primera cadena es léxicamente menor, lo hace poniendo primero a la segunda
            return new StringBuilder(string2).append(string1).toString();
        else return new StringBuilder("Error_Chat_Contigo_mismo")//si son iguales las cadenas, da error
                    .append(new Random().nextInt()).toString();
    }

    /**
     * obtiene el nombre del usuario (nombre apellidos)
     * @param chatUser
     * @return
     */
    public static String getName(UserModel chatUser) {
        return new StringBuilder(chatUser.getFirstName())
                .append(" ")
                .append(chatUser.getLastName())
                .append(" ")
                .toString();
    }

    /**
     * Obtiene el nombre de la imagen que se mande
     * @param contentResolver
     * @param fileUri
     * @return
     */
    public static String getFileName(ContentResolver contentResolver, Uri fileUri) {
        String result= null;

        //si la uri tiene contenido, el cursor recorre la base de datos
        if(fileUri.getScheme().equals("content")){
            Cursor cursor = contentResolver.query(fileUri, null, null, null);
            try {
                if(cursor != null && cursor.moveToFirst())
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }finally {{
                cursor.close();//después cierra la base de datos
            }

            }
        }

        //si no encuentra nada
        if(result == null){
            result = fileUri.getPath();
            int cut = result.lastIndexOf("/");
            if(cut != -1)
                result =  result.substring(cut+1);
        }

        return result;
    }

    /**
     * Muestra las notificaciones
     * @param context
     * @param id
     * @param title
     * @param content
     * @param sender
     * @param roomId
     * @param intent
     */
    public static void showNotification(Context context, int id,
                                        String title, String content,
                                        String sender, String roomId,
                                        Intent intent) {
        PendingIntent pendingIntent = null;
        if(intent != null)
            pendingIntent = PendingIntent.getActivity(context,
                    id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String NOTIFICATION_CHANNEL_ID="com.example.psique";//nombre del canal de la app

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        //comprobar versión
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                            "Psique",
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Chat de Psique");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.MAGENTA);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});//datos de la notificación

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
                !Constants.roomSelected.equals(roomId))//si el usuario actual es distinto al que manda la aplicación y no se está en el chat
            notificationManager.notify(id,notification);//manda la notificación

    }
}
