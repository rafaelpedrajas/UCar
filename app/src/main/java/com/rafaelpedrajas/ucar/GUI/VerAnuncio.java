package com.rafaelpedrajas.ucar.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rafaelpedrajas.ucar.GUI.MainActivity;
import com.rafaelpedrajas.ucar.GUI.Perfil;
import com.rafaelpedrajas.ucar.R;

public class VerAnuncio extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_anuncio);

        //Inicializar toolbar
        ImageButton back = (ImageButton) findViewById(R.id.back);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Anuncio");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAtras();
            }
        });

        //Inicializar variables





        TextView hora = (TextView)findViewById(R.id.hora);
        TextView precio = (TextView)findViewById(R.id.precio);
        TextView plazasLibres = (TextView)findViewById(R.id.plazas);
        ImageButton image = (ImageButton)findViewById(R.id.image);


        hora.setText("09/06/45");
        precio.setText("0.5 €");
        plazasLibres.setText("3");


        //Visitar el perfil de un usuario a traves de un anuncio publicado

        image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), Perfil.class);
                startActivity(i);
            }
        });

        Button botonInscribirse = (Button)findViewById(R.id.botonInscribirse);
        botonInscribirse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
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
