package com.example.psique.models;

import com.cometchat.pro.models.User;
import com.stfalcon.chatkit.commons.models.IUser;

public class UserWrapper implements IUser {

    //atributos
    private User user;

    //constructor
    public UserWrapper(User user) {
        this.user = user;
    }

    //getters
    @Override
    public String getId() {
        return user.getUid();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public String getAvatar() {
        return user.getAvatar();
    }
}
