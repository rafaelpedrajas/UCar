package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Contacto extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

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
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
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
}
