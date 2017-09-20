package com.rafaelpedrajas.ucar.GUI;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rafaelpedrajas.ucar.Clases.Usuario;
import com.rafaelpedrajas.ucar.R;
import com.rafaelpedrajas.ucar.Sesion.SessionManager;

import java.util.HashMap;

public class Contacto extends AppCompatActivity
{
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        TextInputEditText eTEmail = (TextInputEditText)findViewById(R.id.eTEmail);


        if(session.isLoggedIn()){
            HashMap<String, Usuario> user = session.getUserDetails();
            eTEmail.setText(user.get(SessionManager.KEY_USUARIO).getCorreo());
        }

        //Inicializar toolbar
        ImageButton back = (ImageButton) findViewById(R.id.back);
        ImageButton send = (ImageButton) findViewById(R.id.send);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);


        back.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
        tituloVentana.setText("Contacto");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAtras();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enviar = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(enviar);
            }
        });
    }

    private void volverAtras(){
        Intent volver = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(volver);
    }
    @Override
    public void onBackPressed (){
        volverAtras();
    }
}
