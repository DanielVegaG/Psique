package com.example.psique.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.psique.ChatIndividualActivity;
import com.example.psique.Models.ChatInfoModel;
import com.example.psique.Models.UserModel;
import com.example.psique.R;
import com.example.psique.Utils.Constants;
import com.example.psique.ViewHolders.ChatInfoHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatFragment extends Fragment {

    //atributos
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    FirebaseRecyclerAdapter adapter;

    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;
    private Unbinder unbinder;
    private ChatViewModel mViewModel;

    static ChatFragment instance;

    public static ChatFragment getInstance() {
        return instance == null ? new ChatFragment() : instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_chat, container, false);
        initView(itemView);
        loadChatList();
        return itemView;
    }

    private void loadChatList() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.CHAT_LIST_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseRecyclerOptions<ChatInfoModel> options = new FirebaseRecyclerOptions
                .Builder<ChatInfoModel>()
                .setQuery(query, ChatInfoModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<ChatInfoModel, ChatInfoHolder>(options) {

            /**
             * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
             * an item.
             * <p>
             * This new ViewHolder should be constructed with a new View that can represent the items
             * of the given type. You can either create a new View manually or inflate it from an XML
             * layout file.
             * <p>
             * The new ViewHolder will be used to display items of the adapter using
             * {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)}. Since it will be re-used to display
             * different items in the data set, it is a good idea to cache references to sub views of
             * the View to avoid unnecessary {@link View#findViewById(int)} calls.
             *
             * @param parent   The ViewGroup into which the new View will be added after it is bound to
             *                 an adapter position.
             * @param viewType The view type of the new View.
             * @return A new ViewHolder that holds a View of the given view type.
             * @see #getItemViewType(int)
             */
            @NonNull
            @Override
            public ChatInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_chat_item, parent, false);

                return new ChatInfoHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatInfoHolder chatInfoHolder, int position, @NonNull ChatInfoModel chatInfoModel) {
                if (!adapter.getRef(position).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color = generator.getColor(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    TextDrawable.IBuilder builder = TextDrawable.builder()
                            .beginConfig()
                            .withBorder(4)
                            .endConfig()
                            .round();

                    String displayName = FirebaseAuth.getInstance().getCurrentUser().getUid()
                            .equals(chatInfoModel.getCreateId()) ? chatInfoModel.getFriendName() : chatInfoModel.getCreateName();

                    TextDrawable drawable = builder.build(displayName.substring(0, 1), color);
                    chatInfoHolder.iv_chatAvatar.setImageDrawable(drawable);

                    chatInfoHolder.tv_chatName.setText(displayName);
                    chatInfoHolder.tv_chatLastMessage.setText(chatInfoModel.getLastMessage());
                    chatInfoHolder.tv_chatTime.setText(simpleDateFormat.format(chatInfoModel.getLastUpdate()));


                    //evento
                    chatInfoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //Ir a los detalles del chat
                            FirebaseDatabase.getInstance()
                                    .getReference(Constants.USER_REFERENCES)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()
                                            .equals(chatInfoModel.getCreateId()) ?
                                            chatInfoModel.getFriendId() : chatInfoModel.getCreateId())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                UserModel userModel = snapshot.getValue(UserModel.class);
                                                Constants.chatUser = userModel;
                                                Constants.chatUser.setUid(snapshot.getKey());

                                                String roomId = Constants.generateChatRoomId(FirebaseAuth
                                                                .getInstance().getCurrentUser().getUid(),
                                                        Constants.chatUser.getUid());
                                                Constants.roomSelected = roomId;

                                                Log.d("ROOMID",roomId);

                                                //Registrar topic
                                                FirebaseMessaging.getInstance()
                                                        .subscribeToTopic(roomId)
                                                        .addOnSuccessListener(aVoid -> {
                                                            startActivity(new Intent(getContext(), ChatIndividualActivity.class));
                                                        });

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(getContext(), "ERROR: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
                } else {//si es la key del propio usuario, la oculta
                    chatInfoHolder.itemView.setVisibility(View.GONE);
                    chatInfoHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }
        };

        adapter.startListening();
        rv_chat.setAdapter(adapter);
    }

    private void initView(View itemView) {
        unbinder = ButterKnife.bind(this, itemView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_chat.setLayoutManager(layoutManager);
        rv_chat.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter != null) adapter.stopListening();
        super.onStop();
    }

}