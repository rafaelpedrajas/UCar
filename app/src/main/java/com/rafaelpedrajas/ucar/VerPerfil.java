package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class VerPerfil extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        //Inicializar toolbar
        ImageButton back = (ImageButton) findViewById(R.id.back);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Perfil");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
            }
        });

        //Inicializar variables

        TextView nombre = (TextView)findViewById(R.id.nombre);
        TextView coche = (TextView)findViewById(R.id.coche);
        TextView ranking = (TextView)findViewById(R.id.ranking);

        nombre.setText("09/06/45");
        coche.setText("10$");
        ranking.setText("3");

        Button botonModificar = (Button)findViewById(R.id.botonModificar);
        botonModificar.setOnClickListener(new View.OnClickListener()
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
