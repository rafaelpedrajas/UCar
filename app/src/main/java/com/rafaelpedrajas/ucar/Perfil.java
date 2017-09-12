package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
                volverAtras();
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

        //----------------Mostrar estadísticas---------


        final LinearLayout verMasEstadisticas = (LinearLayout) findViewById(R.id.layout_ver_mas);
        final LinearLayout layoutEstadisticas = (LinearLayout) findViewById(R.id.estadisticas);
        final RatingBar rtb = (RatingBar)findViewById(R.id.ratingBar);

        verMasEstadisticas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                layoutEstadisticas.setVisibility(View.VISIBLE);
                verMasEstadisticas.setVisibility(View.GONE);
                rtb.setVisibility(View.GONE);
            }
        });

        //------------Mostrar menos estadísticas----------

        final LinearLayout verMenosEstadisticas = (LinearLayout) findViewById(R.id.layout_ver_menos);

        verMenosEstadisticas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                layoutEstadisticas.setVisibility(View.GONE);
                verMasEstadisticas.setVisibility(View.VISIBLE);
                rtb.setVisibility(View.VISIBLE);
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

    private void volverAtras(){
        Intent volver = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(volver);
    }
    @Override
    public void onBackPressed (){
        volverAtras();
    }


}
