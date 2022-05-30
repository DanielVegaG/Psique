package com.example.psique;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {//para seleccionar los ítems del menú

    //atributos
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //inicializar atributos
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer_layout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);//notificará cuando se seleccione un ítem del menú

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);//para ciegos
        drawer_layout.addDrawerListener(toggle);//agrega el toggle a la lista de listeners que serán notificados por los eventos del drawer
        toggle.syncState();//sincroniza el estado del indicador del drawer con la layout conectada

        if (savedInstanceState == null) {
            //cuando se abra la app, se abra en el Home
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);//esté seleccionado directançmente el nav de home
        }
    }

    /**
     * Se le llama cuando se selecciona un ítem de la action bar
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {//cada case es cada nav del menú que se abre
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                break;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccountFragment()).commit();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);//para que después de entrar en la layout, se cierre el drawer
        return true;
    }

    /**
     * SI está abierto el drawer, al tocar fuera, se cierra
     */
    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}