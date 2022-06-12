package com.example.psique;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.example.psique.Listeners.IFirebaseLoadFailed;
import com.example.psique.Listeners.ILoadTimeFromFirebaseListener;
import com.example.psique.Models.ChatInfoModel;
import com.example.psique.Models.ChatMessageModel;
import com.example.psique.Models.FCMResponse;
import com.example.psique.Models.FCMSendData;
import com.example.psique.Remote.IFCMService;
import com.example.psique.Remote.RetrofitFCMClient;
import com.example.psique.Utils.Constants;
import com.example.psique.ViewHolders.ChatPictureHolder;
import com.example.psique.ViewHolders.ChatPictureReceiveHolder;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChatIndividualActivity extends AppCompatActivity implements ILoadTimeFromFirebaseListener, IFirebaseLoadFailed {

    //atributos
    private static final int MY_CAMERA_REQUEST_CODE = 7171;//número para controlar cuándo se selecciona hacer una foto
    private static final int MY_RESULT_LOAD_IMAGE = 7172;//número para controlar cuándo se selecciona la galería

    @BindView(R.id.tb_individualChat)
    public Toolbar tb_individualChat;//Toolbar del chat
    @BindView(R.id.iv_individualChatCamera)
    public ImageView iv_individualChatCamera;//Icono de cámara del chat
    @BindView(R.id.iv_individualChatImage)
    public ImageView iv_individualChatImage;//Icono de galería del chat
    @BindView(R.id.et_individualChat)
    public AppCompatEditText et_individualChat;//EditText del chat (en el que se escribe)
    @BindView(R.id.iv_individualChatSend)
    public ImageView iv_individualChatSend;//icono de mandar mensaje
    @BindView(R.id.rv_individualChat)
    public RecyclerView rv_individualChat;//recyclerView de los mensajes
    @BindView(R.id.iv_individualChatPreview)
    public ImageView iv_individualChatPreview;//último mensaje del chat
    @BindView(R.id.iv_individualChatAvatar)
    public ImageView iv_individualChatAvatar;//icono de la otra persona
    @BindView(R.id.tv_individualChatName)
    public TextView tv_individualChatName;//nombre de la otra persona


    FirebaseDatabase database;//base de datos
    DatabaseReference chatRef, offSetRef;//referencia al chat y al offSet
    ILoadTimeFromFirebaseListener listener;
    IFirebaseLoadFailed errorListener;

    FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder> adapter;
    FirebaseRecyclerOptions<ChatMessageModel> options;

    Uri fileUri;//uri de la imagen  que se comparta
    StorageReference storageReference;//referencia a la base de datos

    LinearLayoutManager layoutManager;

    IFCMService ifcmService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * si se selecciona el botón de la galería,
     * se abre la galería (o lo que se seleccione)
     * y la foto se pone en el editText de los mensajes
     */
    @OnClick(R.id.iv_individualChatImage)
    void onSelectImageClick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, MY_RESULT_LOAD_IMAGE);//se llama al método onActivityResult
    }

    /**
     * abre la cámara y al sacar una foto se puede mandar
     */
    @OnClick(R.id.iv_individualChatCamera)
    void onCaptureImageClick() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nueva foto");
        values.put(MediaStore.Images.Media.DESCRIPTION, "De tu galería");
        fileUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
        );
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);//se llama al método onActivityResult
    }


    /**
     * Envía el mensaje a la otra persona
     * al darle al botón de enviar
     */
    @OnClick(R.id.iv_individualChatSend)
    void onSubmitChatClick() {
        offSetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long offset = snapshot.getValue(Long.class);
                long estimulatedServerTimeinMs = System.currentTimeMillis() + offset;

                listener.onLoadOnlyTimeSuccess(estimulatedServerTimeinMs);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorListener.onError(error.getMessage());
            }
        });
    }

    ;


    /**
     * Dependiendo si se escoge para mandar una imagen
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {//si se selecciona sacar una foto
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap thumbNail = MediaStore.Images.Media
                            .getBitmap(
                                    getContentResolver(),
                                    fileUri
                            );
                    iv_individualChatPreview.setImageBitmap(thumbNail);
                    iv_individualChatPreview.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException fnfe) {
                    Toast.makeText(this, "ERROR: " + fnfe.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException ioe) {
                    Toast.makeText(this, "ERROR: " + ioe.getMessage(), Toast.LENGTH_SHORT).show();

                }
            } else if (requestCode == MY_RESULT_LOAD_IMAGE) {//si se selecciona la galería
                if (requestCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        InputStream inputStream = getContentResolver()
                                .openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                        iv_individualChatPreview.setImageBitmap(selectedImage);
                        iv_individualChatPreview.setVisibility(View.VISIBLE);
                        fileUri = imageUri;
                    } catch (FileNotFoundException fnfe) {
                        Toast.makeText(this, "ERROR: " + fnfe.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, escoge una imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);

        initViews();//inicializa los atributos
        loadChatContent();//carga el chat
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        Constants.roomSelected = "";
        super.onDestroy();
    }

    /**
     * carga el contenido del chat
     */
    private void loadChatContent() {
        String receiverId = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();//id del destnatario del mensaje

        adapter = new FirebaseRecyclerAdapter<ChatMessageModel, RecyclerView.ViewHolder>(options) {

            /**
             *
             * @param position
             * @return un número al onCreateViewHolder, dependiendo del tipo de mensaje y del remitente
             */
            @Override
            public int getItemViewType(int position) {
                if (adapter.getItem(position).getSenderId()
                        .equals(receiverId)) //si el mensaje es propio
                    return !adapter.getItem(position).isPicture() ? 0 : 1;//si es texto, da valor 0, si es imagen 1

                else
                    return !adapter.getItem(position).isPicture() ? 2 : 3;//igual que el anterior, pero con 2 y 3 para el que lo recibe
            }

            /**
             * Se da valor a los atributos del holder que corresponda según el tipo de dato
             * @param viewHolder
             * @param i
             * @param chatMessageModel
             */
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull ChatMessageModel chatMessageModel) {
                Log.d("Strings", "Contenido: "+chatMessageModel.getContent()+"");
                if (viewHolder instanceof ChatTextHolder) {//si el holder es un mensaje de texto propio
                    ChatTextHolder chatTextHolder = (ChatTextHolder) viewHolder;

                    //se da valor a los atributos del holder de los mensajes de texto propios
                    chatTextHolder.tv_ownChatMessage.setText(chatMessageModel.getContent());
                    chatTextHolder.tv_ownChatTime.setText(
                            DateUtils.getRelativeTimeSpanString(chatMessageModel.getTimeStamp(),
                                            Calendar.getInstance().getTimeInMillis(), 0)
                                    .toString());

                } else if (viewHolder instanceof ChatTextReceiveHolder) {//si el holder es mensaje de texto recibido
                    ChatTextReceiveHolder chatTextReceiveHolder = (ChatTextReceiveHolder) viewHolder;

                    //se da valor a los atributos del holder de los mensajes de texto recibidos
                    Log.d("Strings", "Contenido: "+chatMessageModel.getContent()+"");
                    chatTextReceiveHolder.tv_friendChatMessage.setText(chatMessageModel.getContent());
                    chatTextReceiveHolder.tv_friendChatTime.setText(
                            DateUtils.getRelativeTimeSpanString(chatMessageModel.getTimeStamp(),
                                            Calendar.getInstance().getTimeInMillis(), 0)
                                    .toString());

                } else if (viewHolder instanceof ChatPictureHolder) {//si holder es una foto propia
                    ChatPictureHolder chatPictureHolder = (ChatPictureHolder) viewHolder;

                    //se da valor a los atributos del holder de foto propia
                    chatPictureHolder.tv_ownMessagePictureText.setText(chatMessageModel.getContent());
                    chatPictureHolder.tv_ownMessagePictureTime.setText(
                            DateUtils.getRelativeTimeSpanString(chatMessageModel.getTimeStamp(),
                                            Calendar.getInstance().getTimeInMillis(), 0)
                                    .toString());
                    Glide.with(ChatIndividualActivity.this)
                            .load(chatMessageModel.getPictureLink())
                            .into(chatPictureHolder.iv_ownMessagePicturePreview);

                } else if (viewHolder instanceof ChatPictureReceiveHolder) {//si holder es una foto recibida
                    ChatPictureReceiveHolder chatPictureReceiveHolder = (ChatPictureReceiveHolder) viewHolder;

                    //se da valor a los atributos del holder de foto recibida
                    chatPictureReceiveHolder.tv_friendMessagePictureText.setText(chatMessageModel.getContent());
                    chatPictureReceiveHolder.tv_friendMessagePictureTime.setText(
                            DateUtils.getRelativeTimeSpanString(chatMessageModel.getTimeStamp(),
                                            Calendar.getInstance().getTimeInMillis(), 0)
                                    .toString());

                    Glide.with(ChatIndividualActivity.this)
                            .load(chatMessageModel.getPictureLink())
                            .into(chatPictureReceiveHolder.iv_friendMessagePicturePreview);
                }

            }

            /**
             * Devuelve el holder que corresponda según el tipo de mensaje y del remitente
             * Devuelve el holder del usuario contrario con el mismo tipo de mensaje
             * En este holder va una vista que es un LinearLayoutInflater con el layout del mensaje
             * @param parent
             * @param viewType
             * @return
             */
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                if (viewType == 0) {//mensaje de texto propio
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_message_text_own, parent, false);
                    return new ChatTextReceiveHolder(view);
                } else if (viewType == 1) { //imagen propia
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_message_picture_own, parent, false);
                    return new ChatPictureReceiveHolder(view);
                } else if (viewType == 2) { //mensaje de texto de la otra persona
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_message_text_friend, parent, false);
                    return new ChatTextHolder(view);
                } else { //imagen de la otra persona
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_message_picture_friend, parent, false);
                    return new ChatPictureHolder(view);
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
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {


                    rv_individualChat.scrollToPosition(positionStart);
                }
            }
        });
        rv_individualChat.setAdapter(adapter);//le da al recyclerView el adapter con la información
    }


    /**
     * INICIALIZA LOS ATRIBUTOS ANTES DE EMPEZAR A TRATARLOS
     * Se preparan las variables para lo que se tenga que hacer con ellas
     */
    private void initViews() {

        ifcmService = RetrofitFCMClient.getInstance().create(IFCMService.class);

        listener = this;
        errorListener = this;
        database = FirebaseDatabase.getInstance();
        chatRef = database.getReference(Constants.CHAT_REFERENCE);

        offSetRef = database.getReference(".info/serverTimeOffset");

        Query query = chatRef.child(Constants.generateChatRoomId(
                Constants.chatUser.getUid(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        )).child(Constants.CHAT_DETAIL_REFERENCE);

        options = new FirebaseRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();
        ButterKnife.bind(this);//hace el binding e inyecta todas las vistas
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_individualChat.setLayoutManager(layoutManager);
        rv_individualChat.setAdapter(adapter);


        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(Constants.chatUser.getUid());
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();

        //con el método de encima se da un color a las fotos de perfil
        TextDrawable drawable = builder.build(Constants.chatUser.getFirstName().substring(0, 1), color);
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

    /**
     * mete todos los datos dentro del ChatMessageModel para enviarse
     *
     * @param estimateTimeInMs
     */
    @Override
    public void onLoadOnlyTimeSuccess(long estimateTimeInMs) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setName(Constants.getName(Constants.currentUser));
        chatMessageModel.setContent(et_individualChat.getText().toString());
        chatMessageModel.setTimeStamp(estimateTimeInMs);
        chatMessageModel.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());


        if (fileUri == null) {//si no es una imagen
            chatMessageModel.setPicture(false);
            //guarda el chat en la base de datos
            submitChatToFirebase(chatMessageModel, chatMessageModel.isPicture(), estimateTimeInMs);
        } else
            uploadPicture(fileUri, chatMessageModel, estimateTimeInMs);

    }

    /**
     * Sube la foto a Firebase
     *
     * @param fileUri
     * @param chatMessageModel
     * @param estimateTimeInMs
     */
    private void uploadPicture(Uri fileUri, ChatMessageModel chatMessageModel, long estimateTimeInMs) {
        AlertDialog dialog = new AlertDialog.Builder(ChatIndividualActivity.this)
                .setCancelable(false)
                .setMessage("Por favor, espera...")
                .create();
        dialog.show();

        String fileName = Constants.getFileName(getContentResolver(), fileUri);
        String path = new StringBuilder(Constants.chatUser.getUid())
                .append("/")
                .append(fileName)
                .toString();//ruta de la imagen
        storageReference = FirebaseStorage.getInstance()
                .getReference()
                .child(path);//da valor a la referencia hacia la base de datos

        UploadTask uploadTask = storageReference.putFile(fileUri);
        //Crear task
        Task<Uri> task = uploadTask.continueWithTask(task1 -> {
            if (!task1.isSuccessful())
                Toast.makeText(this, "Error al subir la foto", Toast.LENGTH_SHORT).show();
            return storageReference.getDownloadUrl();
        }).addOnCompleteListener(task12 -> {
            if (task12.isSuccessful()) {
                String url = task12.getResult().toString();
                dialog.dismiss();

                chatMessageModel.setPicture(true);
                chatMessageModel.setPictureLink(url);

                submitChatToFirebase(chatMessageModel, chatMessageModel.isPicture(), estimateTimeInMs);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Guarda la conversación en Firebase
     *
     * @param chatMessageModel
     * @param isPicture
     * @param estimateTimeInMs
     */
    private void submitChatToFirebase(ChatMessageModel chatMessageModel, boolean isPicture, long estimateTimeInMs) {
        chatRef.child(Constants.generateChatRoomId(Constants.chatUser.getUid(),
                        FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())//si existe el chat
                            appendChat(chatMessageModel, isPicture, estimateTimeInMs);//une la conversación al chat existente
                        else
                            createChat(chatMessageModel, isPicture, estimateTimeInMs);//crea chat nuevo
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Une la conversación al chat existente
     *
     * @param chatMessageModel
     * @param isPicture
     * @param estimateTimeInMs
     */
    private void appendChat(ChatMessageModel chatMessageModel, boolean isPicture, long estimateTimeInMs) {
        Map<String, Object> update_data = new HashMap<>();
        update_data.put("lastUpdate", estimateTimeInMs);


        if (isPicture)//si es una foto
            update_data.put("lastMessage", "<Image>");//si es una imagen, introduce una imagen
        else//si es texto, introduce texto
            update_data.put("lastMessage", chatMessageModel.getContent());


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
                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        //añadir en la referencia del chat
                                        String roomId = Constants.generateChatRoomId(Constants.chatUser.getUid(),
                                                FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        chatRef.child(roomId)
                                                .child(Constants.CHAT_DETAIL_REFERENCE)
                                                .push()
                                                .setValue(chatMessageModel)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //limpiar
                                                            et_individualChat.setText("");
                                                            et_individualChat.requestFocus();
                                                            if (adapter != null)
                                                                adapter.notifyDataSetChanged();

                                                            //limpiar la anterior thumbnail
                                                            if (isPicture) {
                                                                fileUri = null;
                                                                iv_individualChatPreview.setVisibility(View.GONE);
                                                            }

                                                            //enviar notificaciones
                                                            sendNotificationToFriend(chatMessageModel, roomId);
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    /**
     * Enviar notificaciones a la otra persona
     * @param chatMessageModel
     * @param roomId
     */
    private void sendNotificationToFriend(ChatMessageModel chatMessageModel, String roomId) {
        Map<String, String> notiData = new HashMap<>();
        notiData.put(Constants.NOTIF_TITLE, "Message from " + chatMessageModel.getName());//título
        notiData.put(Constants.NOTIF_CONTENT, chatMessageModel.getContent());//contenido
        notiData.put(Constants.NOTIF_SENDER, FirebaseAuth.getInstance().getCurrentUser().getUid());//persona que la envía
        notiData.put(Constants.NOTIF_ROOM_ID, roomId);//id del chat

        FCMSendData sendData = new FCMSendData("/topics/" + roomId, notiData);

        compositeDisposable.add(
                ifcmService.sendNotification(sendData)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FCMResponse>() {
                            @Override
                            public void accept(FCMResponse fcmResponse) throws Exception {

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(ChatIndividualActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
        );

    }


    /**
     * crea el chat
     * @param chatMessageModel
     * @param isPicture
     * @param estimateTimeInMs
     */
    private void createChat(ChatMessageModel chatMessageModel, boolean isPicture, long estimateTimeInMs) {
        ChatInfoModel chatInfoModel = new ChatInfoModel();
        chatInfoModel.setCreateId(FirebaseAuth.getInstance().getCurrentUser().getUid());//id del chat
        chatInfoModel.setFriendName(Constants.getName(Constants.chatUser));//nombre de la otra persona
        chatInfoModel.setFriendId(Constants.chatUser.getUid());//id del usuario
        chatInfoModel.setCreateName(Constants.getName(Constants.currentUser));


        if (isPicture)
            chatInfoModel.setLastMessage("<Image>");//si es una imagen, introduce una imagen
        else
            chatInfoModel.setLastMessage(chatMessageModel.getContent());//si es texto, texto

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
                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        //añadir en la referencia del chat
                                        String roomId = Constants.generateChatRoomId(Constants.chatUser.getUid(),
                                                FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        chatRef.child(roomId)
                                                .child(Constants.CHAT_DETAIL_REFERENCE)
                                                .push()
                                                .setValue(chatMessageModel)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ChatIndividualActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //limpiar
                                                            et_individualChat.setText("");
                                                            et_individualChat.requestFocus();
                                                            if (adapter != null)
                                                                adapter.notifyDataSetChanged();

                                                            //limpiar la anterior thumbnail
                                                            if (isPicture) {
                                                                fileUri = null;
                                                                iv_individualChatPreview.setVisibility(View.GONE);
                                                            }

                                                            //enviar notificaciones
                                                            sendNotificationToFriend(chatMessageModel, roomId);

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
        Toast.makeText(this, "ERROR: " + message, Toast.LENGTH_SHORT).show();
    }
}