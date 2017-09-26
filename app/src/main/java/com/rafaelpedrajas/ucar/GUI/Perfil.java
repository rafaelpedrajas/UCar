package com.rafaelpedrajas.ucar.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rafaelpedrajas.ucar.Clases.Usuario;
import com.rafaelpedrajas.ucar.R;
import com.rafaelpedrajas.ucar.Sesion.SessionManager;

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
        TextView nombreProvincia = (TextView) findViewById(R.id.tvProvinciaValor);
        TextView nombreUniversidad = (TextView) findViewById(R.id.tvUniversidadValor);
        TextView nombreCoche = (TextView) findViewById(R.id.tvCocheValor);


        RatingBar rtbPuntualidad = (RatingBar) findViewById(R.id.ratingBarPuntualidad);
        RatingBar rtbAmabilidad = (RatingBar) findViewById(R.id.ratingBarAmabilidad);
        RatingBar rtbConduccion = (RatingBar) findViewById(R.id.ratingBarConduccion);



        //-------FIN INICIALIZAR INTERFAZ------------

        //------  RELLENAR INTERFAZ------

        HashMap<String, String> user = session.getUserDetails();
        /*
        nombre.setText(user.get(SessionManager.KEY_NOMBRE));
        telefono.setText(user.get(SessionManager.KEY_TELEFONO));
        */

        //NOMBRE
        String nombreYApellidos=user.get(SessionManager.KEY_NOMBRE)+" "+user.get(SessionManager.KEY_APELLIDO);
        nombre.setText(nombreYApellidos);

        //VALORACION GENERAL

        float valoracionPuntualidad = Float.parseFloat(user.get(SessionManager.KEY_VALORACION_PUNTUALIDAD));
        float valoracionAmabilidad = Float.parseFloat(user.get(SessionManager.KEY_VALORACION_AMABILIDAD));
        float valoracionConduccion = Float.parseFloat(user.get(SessionManager.KEY_VALORACION_CONDUCCION));

        float valoracionGeneral=valoracionPuntualidad+valoracionAmabilidad+valoracionConduccion;
        valoracionGeneral=valoracionGeneral/3;
        rtb.setRating(valoracionGeneral);

        //ESTADISTICAS
        rtbPuntualidad.setRating(valoracionPuntualidad);
        rtbAmabilidad.setRating(valoracionAmabilidad);
        rtbConduccion.setRating(valoracionConduccion);

        //Telefono
        telefono.setText(user.get(SessionManager.KEY_TELEFONO));

        //Provincia
        nombreProvincia.setText(user.get(SessionManager.KEY_NOMBRE_PROVINCIA));

        //Universidad
        nombreUniversidad.setText(user.get(SessionManager.KEY_NOMBRE_UNIVERSIDAD));

        //COCHE
        String marcaYModelo=user.get(SessionManager.KEY_MARCA_COCHE)+" "+user.get(SessionManager.KEY_MODELO_COCHE);
        nombreCoche.setText(marcaYModelo);

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
