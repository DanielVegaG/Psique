package com.example.psique;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.widget.ViewPager2;

import com.example.psique.Adapters.ChatPagerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    //atributos
    @BindView(R.id.tabDots)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();//inicializa los atributos
        setupViewPager();//establecre un viewPager

        //obtener token para notificaciones
        FirebaseMessaging.getInstance()
                .getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d("TOKEN", s);
                    }
                });
    }

    /**
     * añade el viewPage a la activity
     */
    private void setupViewPager() {
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setAdapter(new ChatPagerAdapter(getSupportFragmentManager(), new Lifecycle() {
            /**
             * Adds a LifecycleObserver that will be notified when the LifecycleOwner changes
             * state.
             * <p>
             * The given observer will be brought to the current state of the LifecycleOwner.
             * For example, if the LifecycleOwner is in {@link State#STARTED} state, the given observer
             * will receive {@link Event#ON_CREATE}, {@link Event#ON_START} events.
             *
             * @param observer The observer to notify.
             */
            @Override
            public void addObserver(@NonNull LifecycleObserver observer) {

            }

            /**
             * Removes the given observer from the observers list.
             * <p>
             * If this method is called while a state change is being dispatched,
             * <ul>
             * <li>If the given observer has not yet received that event, it will not receive it.
             * <li>If the given observer has more than 1 method that observes the currently dispatched
             * event and at least one of them received the event, all of them will receive the event and
             * the removal will happen afterwards.
             * </ul>
             *
             * @param observer The observer to be removed.
             */
            @Override
            public void removeObserver(@NonNull LifecycleObserver observer) {

            }

            /**
             * Returns the current state of the Lifecycle.
             *
             * @return The current state of the Lifecycle.
             */
            @NonNull
            @Override
            public State getCurrentState() {
                return null;
            }
        }));
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override//crea dos pestañas al entrar en "Chat", una pone "Tus chats" y otra "Uuarios"
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Tus chats");
                }else{
                    tab.setText("Usuarios");
                }
            }
        }).attach();
    }

    /**
     * Une los atributos a sus valores
     */
    private void init(){
        ButterKnife.bind(this);
    }

}