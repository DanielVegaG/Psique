package com.example.psique.models;

import com.cometchat.pro.models.TextMessage;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class MessageWrapper implements IMessage {

    //atributos
    private TextMessage textMessage;

    //constructor
    public MessageWrapper(TextMessage textMessage) {
        this.textMessage = textMessage;
    }


    //getters
    @Override
    public String getId() {
        return textMessage.getMuid();
    }

    @Override
    public String getText() {
        return textMessage.getText();
    }

    @Override
    public IUser getUser() {
        return new UserWrapper(textMessage.getSender());
    }

    @Override
    public Date getCreatedAt() {
        return new Date(textMessage.getSentAt() * 1000);
    }
}
