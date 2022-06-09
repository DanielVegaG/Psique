package com.example.psique.Fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.psique.ChatActivity;
import com.example.psique.ChatIndividualActivity;
import com.example.psique.Models.UserModel;
import com.example.psique.R;
import com.example.psique.Utils.Constants;
import com.example.psique.ViewHolders.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PeopleFragment extends Fragment {

    //atributos
    @BindView(R.id.rv_people)
    RecyclerView rv_people;
    FirebaseRecyclerAdapter adapter;
    private Unbinder unbinder;
    private PeopleViewModel mViewModel;
    static PeopleFragment instance;

    public static PeopleFragment getInstance() {
        return instance == null ? new PeopleFragment() : instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_people, container, false);
        initView(itemView);
        loadPeople();
        return itemView;
    }

    private void loadPeople() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.USER_REFERENCES);
        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions
                .Builder<UserModel>()
                .setQuery(query, UserModel.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<UserModel, UserViewHolder>(options) {

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
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_people,parent, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position, @NonNull UserModel userModel) {
                if(!adapter.getRef(position).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color = generator.getColor(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    TextDrawable.IBuilder builder = TextDrawable.builder()
                            .beginConfig()
                            .withBorder(4)
                            .endConfig()
                            .round();
                    TextDrawable drawable = builder.build(userModel.getFirstName().substring(0,1),color);
                    userViewHolder.iv_peopleAvatar.setImageDrawable(drawable);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(userModel.getFirstName()).append(" ").append(userModel.getLastName());
                    userViewHolder.tv_peopleName.setText(stringBuilder.toString());
                    userViewHolder.tv_peopleBio.setText(userModel.getBio());

                    //evento
                    userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Constants.chatUser = userModel;
                            Constants.chatUser.setUid(adapter.getRef(position).getKey());

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
                    });
                }else{//si la key es igual a la del usuario, la oculta
                    userViewHolder.itemView.setVisibility(View.GONE);
                    userViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }
            }
        };


        adapter.startListening();
        rv_people.setAdapter(adapter);
    }

    private void initView(View itemView) {
        unbinder = ButterKnife.bind(this,itemView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_people.setLayoutManager(layoutManager);
        rv_people.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PeopleViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null) adapter.startListening();
    }

    @Override
    public void onStop() {
        if(adapter != null) adapter.stopListening();
        super.onStop();
    }
}