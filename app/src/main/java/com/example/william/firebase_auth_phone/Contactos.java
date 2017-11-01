package com.example.william.firebase_auth_phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contactos extends AppCompatActivity {
        DatabaseReference database;
        String nombre;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contactos_main);

            Bundle datos = this.getIntent().getExtras();
            nombre = datos.getString("nombre");
            data();


        }

    public void  data(){
        FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(nombre,us.getPhoneNumber(),us.getUid());
        database= FirebaseDatabase.getInstance().getReference();
        database.child("User")
                .child(nombre).setValue(user);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(this,Login.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
