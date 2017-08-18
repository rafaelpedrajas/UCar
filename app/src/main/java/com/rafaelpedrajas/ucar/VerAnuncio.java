package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
            }
        });

        //Inicializar variables





        TextView hora = (TextView)findViewById(R.id.hora);
        TextView precio = (TextView)findViewById(R.id.precio);
        TextView plazasLibres = (TextView)findViewById(R.id.plazas);

        hora.setText("09/06/45");
        precio.setText("0.5 €");
        plazasLibres.setText("3");

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
}
