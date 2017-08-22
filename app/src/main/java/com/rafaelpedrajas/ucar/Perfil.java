package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Perfil extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Inicializar toolbar
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
    }
}
