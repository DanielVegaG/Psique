package com.example.psique;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.psique.Listeners.IFirebaseLoadFailed;
import com.example.psique.Listeners.ILoadTimeFromFirebaseListener;
import com.example.psique.Models.ChatInfoModel;
import com.example.psique.Models.ChatMessageModel;
import com.example.psique.Utils.Constants;
import com.example.psique.ViewHolders.ChatTextHolder;
import com.example.psique.ViewHolders.ChatTextReceiveHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatIndividualActivity extends AppCompatActivity implements ILoadTimeFromFirebaseListener, IFirebaseLoadFailed {

    //atributos
    private static final int MY_CAMERA_REQUEST_CODE = 7171;
    private static final int MY_RESULT_LOAD_IMAGE = 7172;

    @BindView(R.id.tb_individualChat)
    public Toolbar tb_individualChat;
    @BindView(R.id.iv_individualChatCamera)
    public ImageView iv_individualChatCamera;
    @BindView(R.id.iv_individualChatImage)
    public ImageView iv_individualChatImage;
    @BindView(R.id.et_individualChat)
    public AppCompatEditText et_individualChat;
    @BindView(R.id.iv_individualChatSend)
    public ImageView iv_individualChatSend;
    @BindView(R.id.rv_individualChat)
    public RecyclerView rv_individualChat;
    @BindView(R.id.iv_individualChatPreview)
    public ImageView iv_individualChatPreview;
    @BindView(R.id.iv_individualChatAvatar)
    public ImageView iv_individualChatAvatar;
    @BindView(R.id.tv_individualChatName)
    public TextView tv_individualChatName;


    FirebaseDatabase database;
    DatabaseReference chatRef, offSetRef;
    ILoadTimeFromFirebaseListener listener;
    IFirebaseLoadFailed errorListener;

    FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder> adapter;
    FirebaseRecyclerOptions<ChatMessageModel> options;

    Uri fileUri;

    LinearLayoutManager layoutManager;

    @OnClick(R.id.iv_individualChatSend)
    void onSubmitChatClick(){
        offSetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long offset = snapshot.getValue(Long.class);
                long estimulatedServerTimeinMs = System.currentTimeMillis()+ offset;

                listener.onLoadOnlyTimeSuccess(estimulatedServerTimeinMs);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorListener.onError(error.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if(adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);

        initViews();
        loadChatContent();
    }

    private void loadChatContent() {
        String receiverId = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();

        adapter = new FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder>(options) {

            @Override
            public int getItemViewType(int position) {
                if (adapter.getItem(position).getSenderId()
                        .equals(receiverId)) //si el mensaje es propio
                            return !adapter.getItem(position).isPicture()?0:1;

                else
                    return !adapter.getItem(position).isPicture()?2:3;
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull ChatMessageModel chatMessageModel) {
                if(viewHolder instanceof ChatTextHolder){
                    ChatTextHolder chatTextHolder = (ChatTextHolder) viewHolder;
                    chatTextHolder.tv_ownChatMessage.setText(chatMessageModel.getContent());
                    chatTextHolder.tv_ownChatTime.setText(
                            DateUtils.getRelativeTimeSpanString(chatMessageModel.getTimeStamp(),
                                    Calendar.getInstance().getTimeInMillis(),0)
                                    .toString());
                }else if(viewHolder instanceof ChatTextReceiveHolder){
                    ChatTextReceiveHolder chatTextReceiveHolder = (ChatTextReceiveHolder) viewHolder;
                    chatTextReceiveHolder.tv_friendChatMessage.setText(chatMessageModel.getContent());
                    chatTextReceiveHolder.tv_friendChatTime.setText(
                            DateUtils.getRelativeTimeSpanString(chatMessageModel.getTimeStamp(),
                                            Calendar.getInstance().getTimeInMillis(),0)
                                    .toString());
                }

            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                if(viewType == 0){//mensaje propio
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_message_text_own, parent, false);
                    return new ChatTextReceiveHolder(view);
                }else{ //if(viewType == 2){ //mensaje de la otra persona
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_message_text_friend, parent, false);
                    return new ChatTextHolder(view);
                }

            }
        };


        //desplazamiento automático cuando llega un mensaje
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                if(lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                            lastVisiblePosition == (positionStart - 1))){


                    rv_individualChat.scrollToPosition(positionStart);
                }
            }
        });
    }

    private void initViews() {
        listener = this;
        errorListener = this;
        database = FirebaseDatabase.getInstance();
        chatRef = database.getReference(Constants.CHAT_REFERENCE);

        offSetRef=database.getReference(".info/serverTimeOffSet");

        Query query = chatRef.child(Constants.generateChatRoomId(
                Constants.chatUser.getUid(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        )).child(Constants.CHAT_DETAIL_REFERENCE);

        options = new FirebaseRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this);
        rv_individualChat.setLayoutManager(layoutManager);


        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(Constants.chatUser.getUid());
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();

        TextDrawable drawable = builder.build(Constants.chatUser.getFirstName().substring(0,1),color);
        iv_individualChatAvatar.setImageDrawable(drawable);
        tv_individualChatName.setText(Constants.getName(Constants.chatUser));

        setSupportActionBar(tb_individualChat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tb_individualChat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onLoadOnlyTimeSuccess(long estimateTimeInMs) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setName(Constants.getName(Constants.currentUser));
        chatMessageModel.setContent(et_individualChat.getText().toString());
        chatMessageModel.setTimeStamp(estimateTimeInMs);
        chatMessageModel.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());


        //sólo el chat de texto
        chatMessageModel.setPicture(false);
        submitChatToFirebase(chatMessageModel, chatMessageModel.isPicture(), estimateTimeInMs);
    }

    private void submitChatToFirebase(ChatMessageModel chatMessageModel, boolean isPicture, long estimateTimeInMs) {
        chatRef.child(Constants.generateChatRoomId(Constants.chatUser.getUid(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                            appendChat(chatMessageModel, isPicture, estimateTimeInMs);
                        else
                            createChat(chatMessageModel, isPicture, estimateTimeInMs);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void appendChat(ChatMessageModel chatMessageModel, boolean isPicture, long estimateTimeInMs) {
        Map<String,Object> update_data = new HashMap<>();
        update_data.put("lastUpdate",estimateTimeInMs);


        //sólo texto
        update_data.put("lastMessage",chatMessageModel.getContent());


        //actualizar
        //actualizar en la lista del usuario
        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_LIST_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.chatUser.getUid())
                .updateChildren(update_data)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Guardar success en ChatInfo
                        //Copiar a la Lista de Amigos
                        FirebaseDatabase.getInstance()
                                .getReference(Constants.CHAT_LIST_REFERENCE)
                                .child(Constants.chatUser.getUid())//cambio
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())//cambio
                                .updateChildren(update_data)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        //añadir en la referencia del chat
                                        chatRef.child(Constants.generateChatRoomId(Constants.chatUser.getUid(),
                                                        FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                                .child(Constants.CHAT_DETAIL_REFERENCE)
                                                .push()
                                                .setValue(chatMessageModel)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            //limpiar
                                                            et_individualChat.setText("");
                                                            et_individualChat.requestFocus();
                                                            if(adapter!=null)
                                                                adapter.notifyDataSetChanged();
                                                        }

                                                    }
                                                });
                                    }
                                });


                    }
                });

    }

    private void createChat(ChatMessageModel chatMessageModel, boolean isPicture, long estimateTimeInMs) {
        ChatInfoModel chatInfoModel = new ChatInfoModel();
        chatInfoModel.setCreateId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        chatInfoModel.setFriendName(Constants.getName(Constants.chatUser));
        chatInfoModel.setFriendId(Constants.chatUser.getUid());
        chatInfoModel.setCreateName(Constants.getName(Constants.currentUser));


        //sólo texto
        chatInfoModel.setLastMessage(chatMessageModel.getContent());

        chatInfoModel.setLastUpdate(estimateTimeInMs);
        chatInfoModel.setCreateDate(estimateTimeInMs);


        //guardarlo en Firebase
        //añadir en la lista de chats del usuario
        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_LIST_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.chatUser.getUid())
                .setValue(chatInfoModel)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Guardar success en ChatInfo
                        //Copiar a la Lista de Amigos
                        FirebaseDatabase.getInstance()
                                .getReference(Constants.CHAT_LIST_REFERENCE)
                                .child(Constants.chatUser.getUid())//cambio
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())//cambio
                                .setValue(chatInfoModel)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        //añadir en la referencia del chat
                                        chatRef.child(Constants.generateChatRoomId(Constants.chatUser.getUid(),
                                                FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                                .child(Constants.CHAT_DETAIL_REFERENCE)
                                                .push()
                                                .setValue(chatMessageModel)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            //limpiar
                                                            et_individualChat.setText("");
                                                            et_individualChat.requestFocus();
                                                            if(adapter!=null)
                                                                adapter.notifyDataSetChanged();
                                                        }

                                                    }
                                                });
                                    }
                                });


                    }
                });
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, "ERROR: "+ message, Toast.LENGTH_SHORT).show();
    }
}