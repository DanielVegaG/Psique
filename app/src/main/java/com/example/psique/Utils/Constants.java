package com.example.psique.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.example.psique.Models.UserModel;

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
}
