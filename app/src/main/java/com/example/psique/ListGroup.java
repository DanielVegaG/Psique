package com.example.psique;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.GroupsRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.Group;

import java.util.List;

public class ListGroup extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ListGroup.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_group);

        getGroupList();
    }

    private void getGroupList() {
        GroupsRequest groupsRequest = new GroupsRequest.GroupsRequestBuilder().build();
        groupsRequest.fetchNext(new CometChat.CallbackListener<List<Group>>() {
            @Override
            public void onSuccess(List <Group> list) {
                updateUI(list);
            }
            @Override
            public void onError(CometChatException e) {
                Toast.makeText(ListGroup.this, "Hubo un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(List<Group> list) {
        RecyclerView rv_listGroup= findViewById(R.id.rv_listGroup);
        rv_listGroup.setLayoutManager(new LinearLayoutManager(this));
        ListGroupAdapter listGroupAdapter = new ListGroupAdapter(list,this);
        rv_listGroup.setAdapter(listGroupAdapter);
    }
}