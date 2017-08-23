package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class Perfil extends AppCompatActivity
{
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();


        //INICIALIZAR TOOLBAR
        ImageButton back = (ImageButton) findViewById(R.id.back);
        ImageButton edit = (ImageButton) findViewById(R.id.edit);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        tituloVentana.setText("Perfil");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
            }
        });

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),EditarPerfil.class);
                startActivity(i);
            }
        });

        //Fin iniciar toolbar


        //-----------INICIALIZAR INTERFAZ---------

        TextView nombre = (TextView) findViewById(R.id.tvNombre);
        TextView telefono = (TextView) findViewById(R.id.tvTelefonoValor);



        //-------FIN INICIALIZAR INTERFAZ------------

        //------  RELLENAR INTERFAZ------

        HashMap<String, String> user = session.getUserDetails();
        nombre.setText(user.get(SessionManager.KEY_NOMBRE));
        telefono.setText(user.get(SessionManager.KEY_TELEFONO));

        //----FIN RELLENAR INTERFAZ----
    }


}
